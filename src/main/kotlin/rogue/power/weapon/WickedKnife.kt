package rogue.power.weapon

import rogue.cards.AbstractWeaponPowerCard

class WickedKnife(
    damage: Int = 3,
    durability: Int = 3,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = WickedKnife::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    upgraded = upgraded
) {
    override fun makeCopy(): AbstractWeaponPowerCard {
        val wickedKnife = rogue.cards.power.WickedKnife(initialDamage, initialDurability)
        if(upgraded){
            wickedKnife.upgrade()
        }
        return wickedKnife
    }

}