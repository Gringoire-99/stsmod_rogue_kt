package rogue.power.weapon

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import rogue.cards.AbstractWeaponPowerCard
import rogue.power.common.StealthPower
import utils.makeId

class Shadowblade(
    damage: Int = 4,
    duration: Int = 2,
    upgraded: Boolean = false,
    magic: Int = 4
) : AbstractWeaponPower(
    rawName = Shadowblade::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    magic = magic,
    upgraded = upgraded
) {
    init {
        addToBot(ApplyPowerAction(owner, owner, StealthPower(owner)))
    }

    override var damage: Int
        get() {
            val add = if (owner?.hasPower(StealthPower::class.makeId()) == true) magic else 0
            return initialDamage + additionalDamage + add
        }
        set(value) {
            additionalDamage += value - damage
            flash()
            updatePowerDesc()
        }
    override var tempLoseDuration: Int = 0
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