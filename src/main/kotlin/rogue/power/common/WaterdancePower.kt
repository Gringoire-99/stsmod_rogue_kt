package rogue.power.common

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.mods.TempCardMod
import utils.addMod

class WaterdancePower(owner: AbstractPlayer, stackAmount: Int = 1) :
    UsageLimitPower(rawName = WaterdancePower::class.simpleName.toString(), owner = owner, stackAmount = stackAmount) {

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (costForTurn == 1 && !freeToPlayOnce) {
                usePower {
                    addToBot(MakeTempCardInHandAction(this.makeStatEquivalentCopy().apply {
                        freeToPlayOnce = true
                        addMod(TempCardMod())
                    }))
                }
            }
        }
    }

}