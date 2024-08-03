package rogue.power.weapon

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import rogue.cards.AbstractWeaponPowerCard
import rogue.power.common.StealthPower
import utils.makeId

class Shadowblade(
    damage: Int = 4,
    durability: Int = 2,
    upgraded: Boolean = false,
    magic: Int = 2
) : AbstractWeaponPower(
    rawName = Shadowblade::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    magic = magic,
    upgraded = upgraded
) {
    init {
        addToBot(ApplyPowerAction(owner, owner, StealthPower(owner)))
    }

    override var damage: Int
        get() {
            val d = initialDamage + additionalDamage
            return if (owner?.hasPower(StealthPower::class.makeId()) == true) d * magic else d
        }
        set(value) {
            additionalDamage += value - damage
            flash()
            updatePowerDesc()
        }
    override var tempLoseDurability: Int = 0
        set(value) {
            if (owner.hasPower(StealthPower::class.makeId()) && value > 0) {
                return
            }
            field = value
        }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.Shadowblade()
        if (upgraded) c.upgrade()
        return c
    }
}