package utils

import Core
import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import common.TradeCard
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import rogue.action.CardFilter
import rogue.action.DiscoveryAction
import rogue.characters.RogueEnum
import rogue.power.weapon.AbstractWeaponPower
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
    damageInfo: DamageInfo = DamageInfo(p ?: AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL),
) {
    AbstractDungeon.actionManager.addToBottom(
        DamageAction(
            m ?: AbstractDungeon.getRandomMonster(),
            damageInfo,
            AbstractGameAction.AttackEffect.BLUNT_LIGHT
        )
    )
}

fun gainBlock(c: AbstractCreature? = AbstractDungeon.player, b: Int) {
    AbstractDungeon.actionManager.addToBottom(GainBlockAction(c, b))
}

fun drawCard(number: Int) {
    AbstractDungeon.actionManager.addToBottom(DrawCardAction(number))
}

//fun gainUniquePower(
//    power: AbstractPower,
//    target: AbstractCreature = AbstractDungeon.player,
//    source: AbstractCreature = AbstractDungeon.player
//) {
//    AbstractDungeon.actionManager.addToBottom(RemoveSpecificPowerAction(target, source, power.ID))
//    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(target, source, power))
//}

fun AbstractCreature.getWeaponPower(): AbstractWeaponPower? {
    return this.getPower(AbstractWeaponPower.id) as AbstractWeaponPower?
}

fun AbstractCreature.isWeaponEquipped(): Boolean {
    return this.getWeaponPower() != null
}

fun AbstractPlayer.attackWithWeapon(
    target: AbstractCreature? = getRandomMonster(),
    damage: Int = 0,
    loseDuration: Int = 0,
) {
    this.getWeaponPower()
        ?.attack(
            target = target,
            damage = damage,
            source = this,
            loseDuration = loseDuration,
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

val tradeStrings = CardCrawlGame.languagePack.getCardStrings(TradeCard::class.makeId())


fun discovery(cardFilter: CardFilter = CardFilter(), cb: (card: AbstractCard) -> Unit = {}) {
    AbstractDungeon.actionManager.addToBottom(DiscoveryAction(cardFilter, cb))
}

fun canPlayTradeCard(card: AbstractCard): Boolean {
    val currentEnergy = EnergyPanel.getCurrentEnergy()
    if (currentEnergy > 1) {
        return true
    } else {
        return card.hasEnoughEnergy()
    }
}

fun AbstractCard.mimic(target: AbstractCard) {
    if (upgraded) {
        target.upgrade()
    }
    if (target.cost == -1) {
        energyOnUse = target.energyOnUse
    }

    cost = target.cost
    costForTurn = target.costForTurn
    freeToPlayOnce = target.freeToPlayOnce
    rawDescription = target.rawDescription
    type = target.type
    portrait = target.portrait
    rarity = target.rarity
    this.target = target.target
    tags = target.tags
    exhaust = target.exhaust
    isEthereal = target.isEthereal
    this.cardsToPreview = target.cardsToPreview
    this.baseDamage = target.baseDamage
    this.damage = this.baseDamage
    this.baseBlock = target.baseBlock
    this.block = this.baseBlock
    this.baseMagicNumber = target.baseMagicNumber
    this.magicNumber = this.baseMagicNumber
    this.initializeDescription()
}

fun generateCardChoices(cardFilter: CardFilter = CardFilter(), number: Int = 4): ArrayList<AbstractCard> {
    val allCards = CardLibrary.getAllCards() as ArrayList<AbstractCard>
    val result = arrayListOf<AbstractCard>()
    if (cardFilter.predicate != null) {
        val abstractCards = allCards.filter(cardFilter.predicate) as ArrayList<AbstractCard>
        repeat(4) {
            result.add(abstractCards.random().makeSameInstanceOf())
        }
    } else {

        val cardType: (AbstractCard) -> Boolean = {
            it.type in cardFilter.cardType
        }
        val cardRarity: (AbstractCard) -> Boolean = {
            it.rarity in cardFilter.cardRarity
        }
        val cardColor: (AbstractCard) -> Boolean = {
            it.color in cardFilter.cardColor
        }
        val costFilter: (AbstractCard) -> Boolean = {
            cardFilter.costFilter(it.cost)
        }
        val abstractCards = allCards.filter {
            cardType(it) && cardType(it) && cardRarity(it) && cardColor(it) && costFilter(it)
        } as ArrayList<AbstractCard>
        repeat(number) {
            val c = abstractCards.random().makeSameInstanceOf()
            if (cardFilter.isUpgraded) {
                c.upgrade()
            }
            result.add(c)
        }
    }
    return result
}

fun AbstractCard.addMod(vararg mod: AbstractCardModifier) {
    mod.forEach {
        CardModifierManager.addModifier(this, it)
    }
}

fun AbstractCard.isOtherClassCard(color: CardColor? = RogueEnum.HS_ROGUE_CARD_COLOR): Boolean {
    val c = color ?: RogueEnum.HS_ROGUE_CARD_COLOR
    val type = this.type == CardType.SKILL || this.type == CardType.POWER || this.type == CardType.ATTACK
    val clazz =
        this.color != c && this.color != CardColor.COLORLESS && this.color != CardColor.CURSE

    return type && clazz
}

fun getRandomMonster(): AbstractMonster {
    return AbstractDungeon.getMonsters().getRandomMonster(true)
}