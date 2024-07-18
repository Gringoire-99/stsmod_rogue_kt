package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard

class AfterUsesMod(var uses: Int = Int.MAX_VALUE, val cb: (card: AbstractCard?) -> Unit = {}) : AbstractCardModifier() {


    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        uses--
        return uses == 0
    }

    override fun onRemove(card: AbstractCard?) {
        cb(card)
    }

    override fun makeCopy(): AbstractCardModifier {
        return AfterUsesMod()
    }
}