package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.UpgradeInterface

class UpgradeVariable : DynamicVariable() {
    override fun key(): String {
        return "UC"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        if (c is UpgradeInterface) {
            return c.upgradeCount > 0
        }

        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is UpgradeInterface) {
            return c.upgradeCount
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is UpgradeInterface) {
            return c.upgradeCount
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}