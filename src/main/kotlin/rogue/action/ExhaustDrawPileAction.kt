package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer

class ExhaustDrawPileAction(val p: AbstractPlayer) : AbstractGameAction() {
    override fun update() {

        isDone = true
    }
}