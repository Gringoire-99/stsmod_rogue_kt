package rogue.power.weapon

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect
import rogue.cards.AbstractWeaponPowerCard

class Kingsbane(
    damage: Int = 3,
    duration: Int = 3,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = Kingsbane::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded
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
            updateCard()
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
        val new = rogue.cards.power.Kingsbane()
        new.apply {
            setWeaponCopy(this@Kingsbane)
        }
        AbstractDungeon.effectList.add(ShowCardAndAddToDiscardEffect(new))
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val new = rogue.cards.power.Kingsbane()
        new.apply {
            setWeaponCopy(this@Kingsbane)
        }
        if (upgraded) new.upgrade()
        return new
    }
}