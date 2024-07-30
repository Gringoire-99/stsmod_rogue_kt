package utils

import Core
import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.*
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
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
import rogue.cards.AbstractMimicCard
import rogue.cards.AbstractWeaponPowerCard
import rogue.characters.RogueEnum
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
        vfx(it)
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                it,
                damageInfo,
                damageEffect
            )
        )
    }

}

fun gainBlock(c: AbstractCreature? = AbstractDungeon.player, b: Int) {
    AbstractDungeon.actionManager.addToBottom(GainBlockAction(c, b))
}

fun drawCard(number: Int) {
    AbstractDungeon.actionManager.addToBottom(DrawCardAction(number))
}

fun applyUniquePower(
    target: AbstractCreature = AbstractDungeon.player,
    source: AbstractCreature = AbstractDungeon.player,
    power: AbstractPower
) {
    AbstractDungeon.actionManager.addToTop(RemoveSpecificPowerAction(target, source, power.ID))
    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(target, source, power))
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
    loseDuration: Int = 0,
    attackEffect: AttackEffect = AttackEffect.SLASH_DIAGONAL,
    vfx: (target: AbstractCreature) -> Unit = {}
) {
    this.getWeaponPower()
        ?.attack(
            target = target,
            damage = damage,
            source = this,
            loseDuration = loseDuration,
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
    cb: (card: AbstractCard) -> Unit = {}
) {
    AbstractDungeon.actionManager.addToBottom(DiscoveryAction(cardFilter, upgraded, from, cb))
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
    from: ArrayList<AbstractCard> = CardLibrary.getAllCards()
): ArrayList<AbstractCard> {
    val allCards = from
    val filtered = cardFilter.filter(allCards)
    val result = ArrayList(filtered.take(min(filtered.size, number)).map { it.makeSameInstanceOf() })
    if (upgraded) {
        result.forEach { it.upgrade() }
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
            this.weaponDuration += magic
        }
    }
}

fun AbstractCard.addToQueue(
    card: AbstractCard,
    t: AbstractCreature?,
    purge: Boolean = false,
    random: Boolean = false,
    cb: (AbstractCard) -> Unit = {}
) {
    if (!AbstractDungeon.player.limbo.contains(card) && purge) {
        AbstractDungeon.player.limbo.addToBottom(this)
    }
    this.current_x = card.current_x
    this.current_y = card.current_y
    this.target_x = Settings.WIDTH.toFloat() / 2.0f - 300.0f * Settings.scale
    this.target_y = Settings.HEIGHT.toFloat() / 2.0f
    val m = if (t is AbstractMonster) t else null
    if (t is AbstractMonster) {
        this.calculateCardDamage(t)
    }
    this.purgeOnUse = purge
    if (random) {
        AbstractDungeon.actionManager.addCardQueueItem(
            CardQueueItem(this, true, card.energyOnUse, true, true),
        )
        cb(this)
    } else {
        AbstractDungeon.actionManager.addCardQueueItem(
            CardQueueItem(this, m, card.energyOnUse, true, true),
        )
        cb(this)
    }
}

