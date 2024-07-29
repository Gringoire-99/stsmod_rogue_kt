package rogue.power.common

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.action.PlayTwiceAction
import rogue.power.IAbstractPower
import utils.getRandomMonster
import utils.isOtherClassCard

class PlayTwicePower(owner: AbstractPlayer, amount: Int = 1) :
    IAbstractPower(powerName = PlayTwicePower::class.simpleName.toString(), owner = owner, amount = amount) {
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (usedCard.isOtherClassCard() && amount > 0) {
                addToBot(PlayTwiceAction(usedCard, getRandomMonster()))
                amount--
                if (amount <= 0) {
                    addToBot(RemoveSpecificPowerAction(owner, owner, this@PlayTwicePower))
                }
            }
        }

    }


}