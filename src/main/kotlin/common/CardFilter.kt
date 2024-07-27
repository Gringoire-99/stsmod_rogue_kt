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
    val includeTags: HashSet<CardTags> = hashSetOf(),
    private val randomSpot: Boolean = true
) {
    private val cardTypeF: (AbstractCard) -> Boolean = {
        it.type in this.cardType
    }
    private val cardRarityF: (AbstractCard) -> Boolean = {
        it.rarity in this.cardRarity
    }
    private val cardColorF: (AbstractCard) -> Boolean = {
        it.color !in this.excludeColor
    }
    private val costFilter: (AbstractCard) -> Boolean = {
        this.cost(it.cost)
    }
    private val tagFilterExcludeF: (AbstractCard) -> Boolean = {
        it.tags.all { it !in this.excludeTags }
    }
    private val tagFilterIncludeF: (AbstractCard) -> Boolean = {
        this.includeTags.size == 0 || it.tags.any { t ->
            t in this.includeTags
        }
    }
    private val upgradeFilter: (AbstractCard) -> Boolean = {
        it.upgraded == isUpgraded
    }
    private val filters = arrayListOf(
        cardTypeF, cardRarityF, cardColorF, costFilter, tagFilterExcludeF, tagFilterIncludeF, upgradeFilter
    )

    fun filter(cards: ArrayList<AbstractCard>): ArrayList<AbstractCard> {
        return if (this.predicate != null) {
            ArrayList(cards.filter(this.predicate).apply {
                if (randomSpot) {
                    (this as ArrayList).shuffle()
                }
            })
        } else {
            ArrayList(cards.filter {
                filters.all { f -> f(it) }
            }.apply {
                if (randomSpot) {
                    (this as ArrayList).shuffle()
                }
            })

        }
    }
}
