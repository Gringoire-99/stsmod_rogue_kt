package rogue.cards.skill

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.action.DrawCardsAction
import rogue.cards.AbstractRogueCard
import utils.addMod

class SketchyInformation() :
    AbstractRogueCard(
        name = SketchyInformation::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            addMod(InnateMod())
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(DrawCardsAction(amount = magicNumber, cardFilter = CardFilter(predicate = { c ->
            c.cost == 0 || c.costForTurn == 0 || c.freeToPlayOnce
        })))
    }
}