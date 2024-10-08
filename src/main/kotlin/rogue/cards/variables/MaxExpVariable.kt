package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.LevelInterface

class MaxExpVariable : DynamicVariable() {
    override fun key(): String {
        return "expM"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is LevelInterface) {
            return c.maxExp
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is LevelInterface) {
            return c.maxExp
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}