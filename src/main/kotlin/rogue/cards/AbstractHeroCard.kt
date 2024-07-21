package rogue.cards

import basemod.cardmods.InnateMod
import utils.addMod

abstract class AbstractHeroCard(rawName: String) :
    AbstractRogueCard(
        name = rawName,
        cost = 3,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        setBlock(5)
    }

    override fun upgrade() {
        useUpgrade {
            addMod(InnateMod())
        }
    }


}