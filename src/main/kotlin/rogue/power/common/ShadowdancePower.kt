package rogue.power.common

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.power.IAbstractPower
import utils.addMod

class ShadowdancePower(owner: AbstractPlayer) :
    IAbstractPower(powerName = ShadowdancePower::class.simpleName.toString(), owner = owner) {
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.let {
            if (it.costForTurn == 0 || it.freeToPlayOnce) {
                val copy = it.makeStatEquivalentCopy()
                copy.apply {
                    cost = 1
                    costForTurn = 1
                    isCostModified = true
                    freeToPlayOnce = false
                    addMod(ExhaustMod(), EtherealMod())
                }
                addToBot(MakeTempCardInHandAction(copy, amount))
            }
        }
    }


}