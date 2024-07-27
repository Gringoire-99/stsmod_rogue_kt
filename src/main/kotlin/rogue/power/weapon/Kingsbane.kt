package rogue.power.weapon

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect
import rogue.cards.AbstractWeaponPowerCard

class Kingsbane(
    damage: Int = 3,
    duration: Int = 3,
    upgraded: Boolean = false,
    magic: Int = 3
) : AbstractWeaponPower(
    rawName = Kingsbane::class.simpleName.toString(),
    damage = damage,
    duration = duration,
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
    override var duration: Int
        get() = super.duration
        set(value) {
            if (value < duration) {
                flash()
                return
            }
            additionalDuration += value - duration
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
        duration++
        AbstractDungeon.effectList.add(ShowCardAndAddToDrawPileEffect(makeCopy(), true, false))
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val new = rogue.cards.power.Kingsbane()
        new.apply {
            setWeaponCopy(this@Kingsbane)
            upgrade()
        }
        return new
    }
}