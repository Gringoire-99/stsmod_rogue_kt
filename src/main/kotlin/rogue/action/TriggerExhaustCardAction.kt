package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.cards.OnExhaustInterface

class TriggerExhaustCardAction : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.player.hand.group.forEach {
            if (it is OnExhaustInterface) {
                it.afterCardExhausted()
            }
        }
        AbstractDungeon.player.limbo.group.forEach {
            if (it is OnExhaustInterface) {
                it.afterCardExhausted()
            }
        }
        isDone = true
    }
}