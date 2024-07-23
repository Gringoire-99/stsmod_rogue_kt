package rogue.power.common

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.power.IAbstractPower
import utils.addMod
import utils.makeId

class WaterdancePower(owner: AbstractPlayer) :
    IAbstractPower(powerName = WaterdancePower::class.simpleName.toString(), owner = owner) {
    companion object {
        var useCount = 0
    }

    init {
        useCount += 1
        updateDescription()
    }

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (useCount > 0 && costForTurn == 1 && !freeToPlayOnce) {
                addToBot(MakeTempCardInHandAction(this.makeStatEquivalentCopy().apply {
                    freeToPlayOnce = true
                    addMod(ExhaustMod(), EtherealMod())
                }))
                useCount--
                updateDescription()
            }
        }
    }

    override fun atStartOfTurnPostDraw() {
        (owner as? AbstractPlayer)?.apply {
            useCount = owner.getPower(WaterdancePower::class.makeId()).amount
        }
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(useCount)
        name = powerString.NAME
    }
}