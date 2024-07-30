package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.BounceAroundAction
import rogue.cards.AbstractRogueCard
import utils.addMod

class BounceAround() :
    AbstractRogueCard(
        name = BounceAround::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            addMod(RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(BounceAroundAction())
    }
}