package rogue.power.weapon

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import rogue.cards.AbstractWeaponPowerCard

class Kingsbane(
    damage: Int = 3,
    durability: Int = 3,
    upgraded: Boolean = false,
    magic: Int = 3
) : AbstractWeaponPower(
    rawName = Kingsbane::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    upgraded = upgraded,
    magic = magic
) {
    override var damage: Int
        get() = super.damage
        set(value) {
            if (value < damage) {
                flash()
                return
            }
            additionalDamage = value - initialDamage
            flash()
            updatePowerDesc()
        }
    override var durability: Int
        get() = super.durability
        set(value) {
            if (value < durability) {
                flash()
                return
            }
            additionalDurability += value - durability
            flash()
            updatePowerDesc()
        }
    override var poisonCount: Int = 0
        set(value) {
            if (value < poisonCount) {
                flash()
                return
            }
            field = value
            updatePowerDesc()
            flash()
        }

    override fun onDestroy() {
        damage += magic
        durability++
        addToBot(MakeTempCardInDrawPileAction(makeCopy(), 1, true, true))
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val new = rogue.cards.power.Kingsbane()
        new.apply {
            setWeaponCopy(this@Kingsbane)
            if (upgraded) upgrade()
        }
        return new
    }
}