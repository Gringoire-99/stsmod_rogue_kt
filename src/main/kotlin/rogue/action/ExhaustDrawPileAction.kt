package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer

class ExhaustDrawPileAction(val p: AbstractPlayer) : AbstractGameAction() {
    override fun update() {
        p.drawPile.group.forEach {
            addToTop(ExhaustSpecificCardAction(it, p.drawPile, true))
        }
        isDone = true
    }
}