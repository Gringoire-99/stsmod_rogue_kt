package rogue.power.weapon

import rogue.cards.AbstractWeaponPowerCard

class BlackwaterCutlass(
    damage: Int = 3,
    duration: Int = 3,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = BlackwaterCutlass::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded
) {
    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.BlackwaterCutlass()
        if (upgraded) c.upgrade()
        return c
    }
}