package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractWeaponPowerCard

class WeaponDurationVariable : DynamicVariable() {
    override fun key(): String {
        return "WDuration"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        if (c is AbstractWeaponPowerCard) {
            return c.isWeaponDurationModified
        }
        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.weaponDuration
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.initialDuration
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}