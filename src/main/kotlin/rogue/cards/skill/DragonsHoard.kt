package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.discovery

class DragonsHoard :
    AbstractRogueCard(
        name = DragonsHoard::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(ExhaustMod())
    }

    private val filter = CardFilter(isUpgraded = true, cardRarity = hashSetOf(CardRarity.RARE))
    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        discovery(filter) {
            if (upgraded) {
                it.freeToPlayOnce = true
            }
        }
    }
}