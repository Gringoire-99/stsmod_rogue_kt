package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.LevelInterface

class ExpVariable : DynamicVariable() {
    override fun key(): String {
        return "exp"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        if (c is LevelInterface) {
            return c.exp > 0
        }

        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is LevelInterface) {
            return c.exp
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is LevelInterface) {
            return c.exp
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}