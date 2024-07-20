package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard

class ReduceCostMod(val amount: Int = 1) : AbstractCardModifier() {
    override fun onInitialApplication(card: AbstractCard?) {
        card?.apply {
            updateCost(-amount)
        }
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            updateCost(amount)
        }
    }

    override fun makeCopy(): AbstractCardModifier {
        return ReduceCostMod()
    }
}