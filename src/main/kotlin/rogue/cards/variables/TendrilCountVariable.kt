package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.characters.Rogue

class TendrilCountVariable : DynamicVariable() {
    override fun key(): String {
        return "TCount"
    }

    override fun isModified(c: AbstractCard?): Boolean {

        return Rogue.chaoticTendrilCount > 1
    }

    override fun value(c: AbstractCard?): Int {
        return Rogue.chaoticTendrilCount
    }

    override fun baseValue(c: AbstractCard?): Int {
        return 1
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}