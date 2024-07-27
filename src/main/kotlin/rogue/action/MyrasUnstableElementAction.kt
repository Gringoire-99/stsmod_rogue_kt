package rogue.action

import basemod.BaseMod
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer

class MyrasUnstableElementAction(val p: AbstractPlayer) : AbstractGameAction() {
    override fun update() {
        val toDraw = BaseMod.MAX_HAND_SIZE - p.hand.size()
        if (toDraw > 0) {
            addToTop(DrawCardAction(toDraw))
            addToBot(ExhaustDrawPileAction(p))
        }
        isDone = true
    }
}