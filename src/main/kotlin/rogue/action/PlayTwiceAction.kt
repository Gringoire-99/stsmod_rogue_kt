package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.cards.Tradeable
import utils.addToQueue

class PlayTwiceAction(val card: AbstractCard, val target: AbstractCreature?) : AbstractGameAction() {
    override fun update() {
        val tmp: AbstractCard = card.makeSameInstanceOf()
        if (tmp is Tradeable) {
            tmp.isEnableTrade = false
        }
        tmp.addToQueue(card, target, true)
        isDone = true
    }
}