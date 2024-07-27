package rogue.power.common

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import utils.addMod
import utils.removeMod

class WaterdancePower(owner: AbstractPlayer, amount: Int = 1) :
    UsageLimitPower(rawName = WaterdancePower::class.simpleName.toString(), owner = owner, amount = amount) {

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (costForTurn == 1 && !freeToPlayOnce) {
                usePower {
                    addToBot(MakeTempCardInHandAction(this.makeStatEquivalentCopy().apply {
                        freeToPlayOnce = true
                        addMod(ExhaustMod(), EtherealMod())
                        removeMod(true, RetainMod.ID)
                        this.isEthereal = true
                        this.exhaust = true
                        this.selfRetain = false
                        this.retain = false
                    }))
                }
            }
        }
    }

}