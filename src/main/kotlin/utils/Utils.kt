package utils

import Core
import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.*
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import common.CardFilter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import rogue.action.DiscoveryAction
import rogue.action.EmptyAction
import rogue.cards.AbstractMimicCard
import rogue.cards.AbstractWeaponPowerCard
import rogue.cards.CavernCard
import rogue.characters.Rogue
import rogue.characters.RogueEnum
import rogue.modconfig.RogueModConfig
import rogue.power.common.CrystalCorePower
import rogue.power.weapon.AbstractWeaponPower
import kotlin.math.min
import kotlin.reflect.KClass

val logger: Logger = LogManager.getLogger(Core.Companion::class.java.name)
fun String.makeId(): String {
    return "${modId}:${this}"
}

fun KClass<*>.makeId(): String {
    return "${modId}:${this.simpleName}"
}

fun getCardImg(type: CardType, cardName: String): String {
    val t: String = when (type) {
        CardType.ATTACK -> "attack"
        CardType.SKILL -> "skill"
        CardType.POWER -> "power"
        else -> {
            logger.info("未知卡片类型")
            throw RuntimeException()
        }
    }
    return "${modId}/cards/$t/$cardName.png"

}

fun getCardString(name: String): CardStrings {
    return CardCrawlGame.languagePack.getCardStrings(name.makeId())
}

fun dealDamage(
    p: AbstractCreature?,
    m: AbstractCreature?,
    damage: Int,
    damageInfo: DamageInfo = DamageInfo(
        p ?: AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL,
    ),
    damageEffect: AttackEffect = AttackEffect.SLASH_DIAGONAL,
    vfx: (target: AbstractCreature) -> Unit = {}
) {
    val target = m ?: getRandomMonster()
    target?.let {
        AbstractDungeon.actionManager.addToTop(
            DamageAction(
                it,
                damageInfo,
                damageEffect
            )
        )
        vfx(it)
    }

}

fun gainBlock(c: AbstractCreature? = AbstractDungeon.player, b: Int) {
    AbstractDungeon.actionManager.addToTop(GainBlockAction(c, b))
}

fun drawCard(number: Int) {
    AbstractDungeon.actionManager.addToBottom(DrawCardAction(number))
}

fun applyUniqueAndStablePower(
    power: AbstractPower,
    target: AbstractCreature = AbstractDungeon.player,
    source: AbstractCreature = AbstractDungeon.player
) {
    AbstractDungeon.actionManager.addToBottom(EmptyAction {
        if (!target.hasPower(power.ID)) {
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(target, source, power))
        }
    })
}

fun AbstractCreature.getWeaponPower(): AbstractWeaponPower? {
    return this.getPower(AbstractWeaponPower.id) as AbstractWeaponPower?
}

fun AbstractCreature.isWeaponEquipped(): Boolean {
    return this.getWeaponPower() != null
}

fun AbstractPlayer.attackWithWeapon(
    target: AbstractMonster? = getRandomMonster(),
    damage: Int = 0,
    loseDurability: Int = 0,
    attackEffect: AttackEffect = AttackEffect.SLASH_DIAGONAL,
    vfx: (target: AbstractCreature) -> Unit = {}
) {
    this.getWeaponPower()
        ?.attack(
            target = target,
            damage = damage,
            source = this,
            loseDurability = loseDurability,
            attackEffect = attackEffect,
            vfx = vfx
        )
}

fun Intent.isAttackIntent(): Boolean {
    val name = this.name
    val name1 = Intent.ATTACK.name
    val name2 = Intent.ATTACK_BUFF.name
    val name3 = Intent.ATTACK_DEFEND.name
    val name4 = Intent.ATTACK_DEBUFF.name
    return name == name1 || name == name2 || name == name3 || name == name4
}


fun discovery(
    cardFilter: CardFilter = CardFilter(),
    upgraded: Boolean = false,
    from: ArrayList<AbstractCard> = CardLibrary.getAllCards(),
    isOtherClassCard: Boolean = true,
    cb: (card: AbstractCard) -> Unit = {}
) {
    AbstractDungeon.actionManager.addToBottom(DiscoveryAction(cardFilter, upgraded, from, isOtherClassCard, cb))
}

fun canPlayTradeCard(card: AbstractCard): Boolean {
    val currentEnergy = EnergyPanel.getCurrentEnergy()
    if (currentEnergy > 1) {
        return true
    } else {
        return card.hasEnoughEnergy()
    }
}
//
//fun AbstractCard.mimic(target: AbstractCard) {
//    if (upgraded) {
//        target.upgrade()
//    }
//    if (target.cost == -1) {
//        energyOnUse = target.energyOnUse
//    }
//    this.target = target.target
//    cost = target.cost
//    costForTurn = target.costForTurn
//    freeToPlayOnce = target.freeToPlayOnce
//    type = target.type
//    portrait = target.portrait
//    jokePortrait = target.jokePortrait
//    rarity = target.rarity
//    this.target = target.target
//    tags = target.tags
//    exhaust = target.exhaust
//    isEthereal = target.isEthereal
//    this.cardsToPreview = target.cardsToPreview
//    this.baseDamage = target.baseDamage
//    this.damage = this.baseDamage
//    this.baseBlock = target.baseBlock
//    this.block = this.baseBlock
//    this.baseMagicNumber = target.baseMagicNumber
//    this.magicNumber = this.baseMagicNumber
//    this.rawDescription = "变化为了 ${target.name}"
//    this.cardsToPreview = target
//    this.initializeDescription()
//    if (!retain) {
//        this.retain = target.retain
//    }
//    if (!selfRetain) {
//        this.selfRetain = target.selfRetain
//    }
//    if (this is Mimicable) {
//        this.targetCard = target
//    }
//
//}

fun generateCardChoices(
    cardFilter: CardFilter = CardFilter(),
    number: Int = 4,
    upgraded: Boolean = false,
    isOtherClassCard: Boolean = true,
    from: ArrayList<AbstractCard> = CardLibrary.getAllCards()
): ArrayList<AbstractCard> {
    if (isOtherClassCard && RogueModConfig.getBoolConfig(RogueModConfig.DiscoverNonModCards)) {
        cardFilter.includeColor.addAll(arrayListOf(CardColor.PURPLE, CardColor.RED, CardColor.GREEN, CardColor.BLUE))
    }
    val filtered = cardFilter.filter(from)
    val result = ArrayList(filtered.take(min(filtered.size, number)).map { it.makeSameInstanceOf() })
    if (upgraded) {
        result.forEach { it.upgrade() }
    }
    val power = AbstractDungeon.player.getPower(CrystalCorePower::class.makeId()) as CrystalCorePower?
    if (power != null) {
        result.forEach {
            power.onAddCard(it)
        }
    }
    return result
}

fun AbstractCard.addMod(vararg mods: AbstractCardModifier) {
    mods.forEach {
        CardModifierManager.addModifier(this, it)
    }
}

fun AbstractCard.removeMod(includeInherent: Boolean = false, vararg ids: String) {
    ids.forEach {
        CardModifierManager.removeModifiersById(this, it, includeInherent)
    }
}

fun AbstractCard.isOtherClassCard(pColor: CardColor? = AbstractDungeon.player?.cardColor): Boolean {
    val thisColor = if (this is AbstractMimicCard) this.mimicTarget?.color else color
    val playerColor = pColor ?: AbstractDungeon.player?.cardColor ?: RogueEnum.HS_ROGUE_CARD_COLOR
    val type = this.type == CardType.SKILL || this.type == CardType.POWER || this.type == CardType.ATTACK
    val clazz =
        thisColor != playerColor && thisColor != CardColor.COLORLESS && thisColor != CardColor.CURSE && thisColor != null
    return type && clazz
}

fun getRandomMonster(): AbstractMonster? {
    return AbstractDungeon.getMonsters().getRandomMonster(true)
}

fun AbstractMonster.isAlive(): Boolean {
    return !this.halfDead && !this.isDying && !this.isEscaping
}

fun AbstractCard.upDamage(amount: Int) {
    this.baseDamage += amount
    this.upgradedDamage = true
}

fun AbstractCard.upBlock(amount: Int) {
    this.baseBlock += amount
    this.upgradedBlock = true
}

fun AbstractCard.upMagicNumber(amount: Int) {
    this.baseMagicNumber += amount
    this.magicNumber = this.baseMagicNumber
    this.upgradedMagicNumber = true
}

fun AbstractCard.upBase(magic: Int) {
    this.apply {
        upDamage(magic)
        upBlock(magic)
        upMagicNumber(magic)
        if (this is AbstractWeaponPowerCard) {
            this.weaponDamage += magic
            this.weaponDurability += magic
            if (this is rogue.cards.power.Kingsbane) {
                this.copy?.let {
                    it.damage = magic
                    it.durability += magic
                    it.magic += magic
                }
            }
        }
        if (this is AbstractMimicCard) {
            this.mimicTarget?.upBase(magic)
            mimicTarget?.let {
                this.mimic(it)
            }
        }
    }
}

fun addToQueue(
    card: AbstractCard,
    t: AbstractCreature?,
    random: Boolean = false,
    purge: Boolean = false,
    cb: (AbstractCard) -> Unit = {}
) {
    val tmp = card.makeStatEquivalentCopy()
    if (purge) {
        AbstractDungeon.player.limbo.addToBottom(tmp)
        tmp.purgeOnUse = true
    }
    tmp.current_x = card.current_x
    tmp.current_y = card.current_y
    tmp.target_x = Settings.WIDTH.toFloat() / 2.0f - 300.0f * Settings.scale
    tmp.target_y = Settings.HEIGHT.toFloat() / 2.0f
    val m = if (t is AbstractMonster) t else null
    if (t is AbstractMonster) {
        tmp.calculateCardDamage(t)
    }
    if (random) {
        AbstractDungeon.actionManager.addCardQueueItem(
            CardQueueItem(tmp, true, card.energyOnUse, true, true), true
        )
    } else {
        AbstractDungeon.actionManager.addCardQueueItem(
            CardQueueItem(tmp, m, card.energyOnUse, true, true), true
        )
    }
    cb(tmp)
}

fun AbstractCard.isCavernCard(): Boolean {
    return this is CavernCard || (this.color != CardColor.COLORLESS
            && CardTags.STARTER_DEFEND !in this.tags
            && CardTags.STARTER_STRIKE !in this.tags)
}

fun useSneakAttack(m: AbstractMonster, cb: (m: AbstractMonster) -> Unit = {}): Boolean {
    if (canSneakAttack(m)) {
        cb(m)
        Rogue.onSneakAttack.forEach {
            it.cb(m)
        }
        return true
    }
    return false
}

fun canSneakAttack(m: AbstractMonster): Boolean {
    val any = Rogue.sneakAttackPredicates.any {
        it.p(m)
    }
    return any || !m.intent.isAttackIntent()
}