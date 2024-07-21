package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard

class TurnEffectMod(private var turn: Int = 1, val onRemoveCb: (c: AbstractCard) -> Unit = {}) : AbstractCardModifier() {

    override fun onRemove(card: AbstractCard?) {
        if (card != null) onRemoveCb(card)
    }

    override fun removeAtEndOfTurn(card: AbstractCard?): Boolean {
        turn--
        return turn == 0
    }

    override fun makeCopy(): AbstractCardModifier {
        return TurnEffectMod()
    }

}