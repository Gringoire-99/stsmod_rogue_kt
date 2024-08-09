package common

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.*
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.characters.RogueEnum


class CardFilter(
    private val isUpgraded: Boolean = false,
    val cardType: HashSet<CardType> = hashSetOf(CardType.SKILL, CardType.POWER, CardType.ATTACK),
    val cardRarity: HashSet<CardRarity> = hashSetOf(CardRarity.COMMON, CardRarity.UNCOMMON, CardRarity.RARE),
    private val predicate: ((card: AbstractCard) -> Boolean)? = null,
    val cost: (Int) -> Boolean = { true },
    private val excludeTags: HashSet<CardTags> = hashSetOf(
        CardTags.HEALING,
        CardTags.STARTER_STRIKE,
        CardTags.STARTER_DEFEND
    ),
    private val excludeColor: HashSet<CardColor> = hashSetOf(
        AbstractDungeon.player?.cardColor ?: RogueEnum.HS_ROGUE_CARD_COLOR,
        CardColor.COLORLESS,
        CardColor.CURSE
    ),
    val includeColor: HashSet<CardColor> = hashSetOf(),
    val includeTags: HashSet<CardTags> = hashSetOf(),
    private val randomSpot: Boolean = true
) {
    private val cardTypeF: (AbstractCard) -> Boolean = {
        cardType.isEmpty() || it.type in this.cardType
    }
    private val cardRarityF: (AbstractCard) -> Boolean = {
        cardRarity.isEmpty() || it.rarity in this.cardRarity
    }
    private val cardColorExcludeF: (AbstractCard) -> Boolean = {
        excludeColor.isEmpty() || it.color !in this.excludeColor
    }
    private val cardColorIncludeF: (AbstractCard) -> Boolean = {
        includeColor.isEmpty() || it.color in includeColor
    }
    private val costFilter: (AbstractCard) -> Boolean = {
        this.cost(it.cost)
    }
    private val tagFilterExcludeF: (AbstractCard) -> Boolean = {
        excludeTags.isEmpty() || it.tags.all { t -> t !in this.excludeTags }
    }
    private val tagFilterIncludeF: (AbstractCard) -> Boolean = {
        this.includeTags.isEmpty() || it.tags.any { t ->
            t in this.includeTags
        }
    }
    private val upgradeFilter: (AbstractCard) -> Boolean = {
        it.upgraded == isUpgraded
    }
    private val filters = arrayListOf(
        cardTypeF,
        cardRarityF,
        cardColorExcludeF,
        cardColorIncludeF,
        costFilter,
        tagFilterExcludeF,
        tagFilterIncludeF,
        upgradeFilter,
    )

    fun filter(cards: ArrayList<AbstractCard>): ArrayList<AbstractCard> {
        val filtered = if (this.predicate != null) {
            ArrayList(cards.filter(this.predicate))
        } else {
            ArrayList(cards.filter {
                filters.all { f -> f(it) }
            })
        }
        if (randomSpot) {
            filtered.shuffle()
        }
        return filtered
    }
}

fun main() {
    println(1 in hashSetOf<Int>())
}