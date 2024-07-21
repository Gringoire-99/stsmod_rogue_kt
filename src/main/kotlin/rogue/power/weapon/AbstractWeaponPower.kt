package rogue.power.weapon

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModContainer
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.*
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.powers.GainStrengthPower
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.WeakPower
import rogue.cards.AbstractWeaponCard
import rogue.cards.AbstractWeaponPowerCard
import rogue.cards.attack.Attack
import rogue.mods.OnAttack
import rogue.mods.OnLastDamageTakenUpdate
import rogue.mods.WeaponDamageMod
import rogue.power.IAbstractPower
import utils.makeId

abstract class AbstractWeaponPower(
    rawName: String,
    damage: Int,
    duration: Int,
    val magic: Int = 0,
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
            updatePowerDesc()
        }
    open var duration: Int
        get() {
            return additionalDuration + initialDuration - tempLoseDuration
        }
        set(value) {
            additionalDuration += value - duration
            flash()
            updatePowerDesc()
        }

    protected val initialDamage: Int
    protected val initialDuration: Int
    protected var additionalDuration = 0
    open var tempLoseDuration = 0
    protected var additionalDamage = 0

    private val weaponName: String
    private var damageColor: Color = Color.GREEN.cpy()
    private var durationColor: Color = Color.GREEN.cpy()
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
    var leechCount = 0
        set(value) {
            field = value
            updatePowerDesc()
            flash()
        }
    val damageModifier: WeaponDamageMod = WeaponDamageMod()

    init {
        this.owner = AbstractDungeon.player
//        具体武器能力名字来源与对应的武器技能卡
        this.weaponName = CardCrawlGame.languagePack.getCardStrings(rawName.makeId()).NAME
        this.initialDamage = damage
        this.initialDuration = duration
        updateDescription()
        baseDamageMods()
        getTempAttackCard()
    }

    private fun baseDamageMods() {
        val leechEffect: OnLastDamageTakenUpdate = { _, lastDamageTaken, _, _ ->
            if (lastDamageTaken > 0 && leechCount > 0) {
                addToTop(HealAction(owner, owner, leechCount))
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
                val d = (poisonCount * 0.4).toInt() + 1
                addToTop(ApplyPowerAction(target, owner, PoisonPower(target, owner, d), d))
                poisonCount -= d
            }
        }
        val drawEffect: OnAttack = { info: DamageInfo?, damageAmount: Int, target: AbstractCreature? ->
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
        val listOfWeaponCard = hashSetOf<AbstractWeaponCard>()
    }

    override fun atStartOfTurnPostDraw() {
        turnAttackCount = 0
        isGetAttackCard = false
        getTempAttackCard()
    }

    /**
     * TODO localization
     */
    fun updatePowerDesc(wDamage: Int = damage, wDuration: Int = duration) {
        this.description = powerString.DESCRIPTIONS[0].format(weaponName, wDamage, wDuration)
        if (leechCount > 0) {
            this.description += " NL 吸血：${leechCount}"
        }
        if (drawCount > 0) {
            this.description += " NL 攻击后抽${drawCount}张牌"
        }
        if (poisonCount > 0) {
            this.description += " NL 毒素：${poisonCount}"
        }
        if (paralysisCount > 0) {
            this.description += " NL 麻痹：${paralysisCount}"
        }

    }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        super.renderAmount(sb, x, y, c)
        if (damage >= initialDamage) {
            damageColor = Color.GREEN.cpy()
        } else {
            damageColor = Color.RED.cpy()
        }

        if (duration >= initialDuration) {
            durationColor = Color.GREEN.cpy()
        } else {
            durationColor = Color.RED.cpy()
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
            duration.toString(),
            x,
            y,
            durationColor
        )
    }


    //使用武器对一个敌人造成武器伤害
    fun attack(
        target: AbstractCreature? = AbstractDungeon.getRandomMonster(),
        source: AbstractCreature? = owner,
        loseDuration: Int = 0,
        damage: Int = 0,
    ) {
        val damageModContainer = DamageModContainer(this, damageModifier)
        val action = DamageAction(
            target,
            BindingHelper.makeInfo(damageModContainer, source, damage, DamageType.NORMAL),
            AbstractGameAction.AttackEffect.BLUNT_LIGHT
        )
        if (duration <= 0) return

        this.addToBot(
            action
        )

        flash()
        loseDuration(loseDuration)

    }

    override fun onRemove() {
        onDestroy()
    }

    open fun onDestroy() {}


    protected fun loseDuration(amount: Int) {
        if (amount <= 0) {
            return
        }
        tempLoseDuration += amount
        if (duration <= 0) {
            addToBot(RemoveSpecificPowerAction(owner, owner, this.ID))
            tempLoseDuration = 0
        }
    }

    final override fun updateDescription() {
        this.description = powerString.DESCRIPTIONS[0]
        name = weaponName
    }

    abstract fun makeCopy(): AbstractWeaponPowerCard
}
