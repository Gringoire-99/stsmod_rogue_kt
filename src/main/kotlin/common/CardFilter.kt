package common

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.*
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.characters.RogueEnum


class CardFilter(
    val isUpgraded: Boolean = false,
    val cardType: HashSet<CardType> = hashSetOf(CardType.SKILL, CardType.POWER, CardType.ATTACK),
    val cardRarity: HashSet<CardRarity> = hashSetOf(CardRarity.COMMON, CardRarity.UNCOMMON, CardRarity.RARE),
//    val cardColor: HashSet<CardColor> = hashSetOf(CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.PURPLE),
    val predicate: ((card: AbstractCard) -> Boolean)? = null,
    val costFilter: (Int) -> Boolean = { true },
    val excludeTags: HashSet<CardTags> = hashSetOf(CardTags.HEALING, CardTags.STARTER_STRIKE, CardTags.STARTER_DEFEND),
    val excludeColor: HashSet<CardColor> = hashSetOf(
        AbstractDungeon.player?.cardColor ?: RogueEnum.HS_ROGUE_CARD_COLOR,
        CardColor.COLORLESS,
        CardColor.CURSE
    )
)