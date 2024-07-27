package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class GearShiftAction(val magic: Int) : AbstractGameAction() {
    override fun update() {
        val hand = AbstractDungeon.player.hand
        val take = hand.group.take(2)
        take.forEach {
            addToTop(DiscardSpecificCardAction(it, hand))
        }
        addToBot(DrawCardAction(magic))
        isDone = true
    }
}