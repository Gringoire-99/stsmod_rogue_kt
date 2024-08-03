package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractWeaponPowerCard

class WeaponDurabilityVariable : DynamicVariable() {
    override fun key(): String {
        return "WDurability"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        if (c is AbstractWeaponPowerCard) {
            return c.isWeaponDurabilityModified
        }
        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.weaponDurability
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.initialDurability
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}