package rogue.power.weapon

import rogue.cards.AbstractWeaponPowerCard

class WickedKnife(
    damage: Int = 3,
    duration: Int = 3,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = WickedKnife::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded
) {
    override fun makeCopy(): AbstractWeaponPowerCard {
        val wickedKnife = rogue.cards.power.WickedKnife(initialDamage, initialDuration)
        if(upgraded){
            wickedKnife.upgrade()
        }
        return wickedKnife
    }

}