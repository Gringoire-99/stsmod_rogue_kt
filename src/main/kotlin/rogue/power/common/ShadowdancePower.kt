package rogue.power.common

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.mods.TempCardMod
import utils.addMod

class ShadowdancePower(owner: AbstractPlayer, stackAmount: Int = 3) :
    UsageLimitPower(rawName = ShadowdancePower::class.simpleName.toString(), owner = owner, stackAmount = stackAmount) {
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.let {
            if ((it.costForTurn == 0 || it.freeToPlayOnce)) {
                usePower {
                    val copy = it.makeStatEquivalentCopy()
                    copy.apply {
                        cost = 1
                        costForTurn = 1
                        isCostModified = true
                        freeToPlayOnce = false
                        addMod(TempCardMod())
                    }
                    addToBot(MakeTempCardInHandAction(copy, 1))
                }
            }
        }
    }


}