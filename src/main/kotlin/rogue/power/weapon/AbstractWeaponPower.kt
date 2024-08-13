package rogue.power.weapon

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModContainer
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.*
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.GainStrengthPower
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.WeakPower
import rogue.cards.AbstractWeaponPowerCard
import rogue.cards.attack.Attack
import rogue.cards.skill.EnvenomWeapon
import rogue.cards.skill.LeechingPoison
import rogue.cards.skill.ParalyticPoison
import rogue.cards.skill.SilverleafPoison
import rogue.mods.OnAttack
import rogue.mods.OnLastDamageTakenUpdate
import rogue.mods.WeaponDamageMod
import rogue.power.IAbstractPower
import utils.getRandomMonster
import utils.makeId

abstract class AbstractWeaponPower(
    rawName: String,
    damage: Int,
    durability: Int,
    var magic: Int = 0,
    val upgraded: Boolean = false
) :
    IAbstractPower(powerName = AbstractWeaponPower::class.simpleName.toString(), amount = -1), NonStackablePower {
    //        武器/耐久变动时 更新描述/卡片信息
    open var damage: Int
        get() {
            return additionalDamage + initialDamage
        }
        set(value) {
            additionalDamage += value - damage
            flash()
        }
    open var durability: Int
        get() {
            return additionalDurability + initialDurability - tempLoseDurability
        }
        set(value) {
            additionalDurability += value - durability
            flash()
        }

    protected val initialDamage: Int
    protected val initialDurability: Int
    protected var additionalDurability = 0
    open var tempLoseDurability = 0
    protected var additionalDamage = 0

    private val weaponName: String
    private val weaponDesc: String
    private var damageColor: Color = Color.GREEN.cpy()
    private var durabilityColor: Color = Color.GREEN.cpy()
    open var poisonCount = 0
        set(value) {
            field = value
            updatePowerDesc()
            flash()
        }
    var drawCount = 0
        set(value) {
            field = value
            updatePowerDesc()
            flash()
        }
    var paralysisCount = 0
        set(value) {
            field = value
            updatePowerDesc()
            flash()
        }
    var lifeStealCount = 0
        set(value) {
            field = value
            updatePowerDesc()
            flash()
        }
    val damageModifier: WeaponDamageMod = WeaponDamageMod()

    init {
        this.owner = AbstractDungeon.player
//        具体武器能力名字来源与对应的武器技能卡
        val cardStrings = CardCrawlGame.languagePack.getCardStrings(rawName.makeId())
        this.weaponName = cardStrings.NAME
        this.weaponDesc = cardStrings.DESCRIPTION
        this.initialDamage = damage
        this.initialDurability = durability
        baseDamageMods()
        getTempAttackCard()
        updatePowerDesc()
    }

    private fun baseDamageMods() {
        val leechEffect: OnLastDamageTakenUpdate = { _, lastDamageTaken, _, _ ->
            if (lastDamageTaken > 0 && lifeStealCount > 0) {
                addToTop(HealAction(owner, owner, lifeStealCount))
            }
        }
        val paralysisEffect: OnLastDamageTakenUpdate = { _, lastDamageTaken, _, target ->
            if (lastDamageTaken > 0 && paralysisCount > 0) {
                addToTop(ApplyPowerAction(target, owner, WeakPower(target, paralysisCount, false), paralysisCount))
                addToTop(ApplyPowerAction(target, owner, StrengthPower(target, -paralysisCount), -paralysisCount))
                addToTop(ApplyPowerAction(target, owner, GainStrengthPower(target, paralysisCount), paralysisCount))
            }
        }
        val poisonEffect: OnLastDamageTakenUpdate = { _, lastDamageTaken, _, target ->
            if (lastDamageTaken > 0 && poisonCount > 0) {
                val d = (poisonCount * 0.2).toInt() + 1
                addToTop(ApplyPowerAction(target, owner, PoisonPower(target, owner, d), d))
                poisonCount -= d
            }
        }
        val drawEffect: OnAttack = { _: DamageInfo?, _: Int, _: AbstractCreature? ->
            if (drawCount > 0) {
                addToTop(DrawCardAction(owner, drawCount))
            }
        }
        damageModifier.cbOfOnLastDamageTakenUpdate.apply {
            add(leechEffect)
            add(paralysisEffect)
            add(poisonEffect)
        }
        damageModifier.cbOfOnAttack.add(drawEffect)
    }


    private fun getTempAttackCard() {
        val find = AbstractDungeon.player.hand.group.find { it.cardID == Attack.id }
        if (find == null && !isGetAttackCard) {
            addToBot(MakeTempCardInHandAction(Attack()))
            isGetAttackCard = true
            flash()
        }
    }


    companion object {
        var combatAttackCount: Int = 0
        var turnAttackCount = 0
        var isGetAttackCard = false
        val id = AbstractWeaponPower::class.makeId()
        fun reset() {
            isGetAttackCard = false
            turnAttackCount = 0
        }
    }

    override fun atStartOfTurn() {
        turnAttackCount = 0
        isGetAttackCard = false
        getTempAttackCard()
    }

    /**
     * TODO localization
     */
    fun updatePowerDesc() {
        updateDescription()
        if (lifeStealCount > 0) {
            this.description += " NL ${LeechingPoison.name} : ${lifeStealCount}"
        }
        if (drawCount > 0) {
            this.description += " NL ${SilverleafPoison.name} : ${drawCount}"
        }
        if (poisonCount > 0) {
            this.description += " NL ${EnvenomWeapon.name} : ${poisonCount}"
        }
        if (paralysisCount > 0) {
            this.description += " NL ${ParalyticPoison.name} : ${paralysisCount}"
        }

    }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        super.renderAmount(sb, x, y, c)
        damageColor = if (damage >= initialDamage) {
            Color.GREEN.cpy()
        } else {
            Color.RED.cpy()
        }

        durabilityColor = if (durability >= initialDurability) {
            Color.GREEN.cpy()
        } else {
            Color.RED.cpy()
        }
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            damage.toString(),
            x,
            y + 15f,
            damageColor
        )
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            durability.toString(),
            x,
            y,
            durabilityColor
        )
    }


    //使用武器对一个敌人造成武器伤害
    fun attack(
        target: AbstractMonster? = getRandomMonster(),
        source: AbstractCreature? = owner,
        loseDurability: Int = 0,
        damage: Int = 0,
        attackEffect: AttackEffect = AttackEffect.SLASH_DIAGONAL,
        vfx: (target: AbstractCreature) -> Unit = {}
    ) {
        val damageModContainer = DamageModContainer(this, damageModifier)
        target?.let {
            val action = DamageAction(
                it,
                BindingHelper.makeInfo(damageModContainer, source, damage, DamageType.NORMAL),
                attackEffect
            )
            if (durability <= 0) return
            vfx(it)
            addToTop(
                action
            )
            flash()
            loseDurability(loseDurability)
        }


    }

    override fun onRemove() {
        onDestroy()
    }

    open fun onDestroy() {}


    protected fun loseDurability(amount: Int) {
        if (amount <= 0) {
            return
        }
        tempLoseDurability += amount
        if (durability <= 0) {
            addToBot(RemoveSpecificPowerAction(owner, owner, this.ID))
            tempLoseDurability = 0
        }
    }

    override fun updateDescription() {
        name = weaponName
        this.description = powerString.DESCRIPTIONS[0]
    }

    abstract fun makeCopy(): AbstractWeaponPowerCard
}
