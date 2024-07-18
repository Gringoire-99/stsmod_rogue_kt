package rogue.cards.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractWeaponPowerCard

class WeaponDamageVariable : DynamicVariable() {
    override fun key(): String {
        return "WDamage"
    }

    override fun isModified(c: AbstractCard?): Boolean {
        if (c is AbstractWeaponPowerCard) {
            return c.isWeaponDamageModified
        }

        return false
    }

    override fun value(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.weaponDamage
        }
        return 0
    }

    override fun baseValue(c: AbstractCard?): Int {
        if (c is AbstractWeaponPowerCard) {
            return c.initialDamage
        }
        return 0
    }

    override fun upgraded(c: AbstractCard?): Boolean {
        return isModified(c)
    }
}