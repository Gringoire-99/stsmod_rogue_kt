package rogue.power.common

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import utils.addMod
import utils.removeMod

class ShadowdancePower(owner: AbstractPlayer, amount: Int = 3) :
    UsageLimitPower(rawName = ShadowdancePower::class.simpleName.toString(), owner = owner, amount = amount) {
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
                        addMod(ExhaustMod(), EtherealMod())
                        removeMod(true, RetainMod.ID)
                        this.isEthereal = true
                        this.exhaust = true
                        this.selfRetain = false
                        this.retain = false
                    }
                    addToBot(MakeTempCardInHandAction(copy, 1))
                }
            }
        }
    }


}