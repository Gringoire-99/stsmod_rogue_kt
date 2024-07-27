package rogue.power.weapon

import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.cards.AbstractWeaponPowerCard
import utils.drawCard

class QuickPick(
    damage: Int = 2,
    duration: Int = 6,
    magic: Int = 1,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = QuickPick::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded,
    magic = magic
) {
    override var tempLoseDuration: Int = 0
        set(value) {
            if (value > field) {
                drawCard(magic)
            }
            field = value
        }

    init {
        damageModifier.cbOfOnAttack.add { _: DamageInfo?, _: Int, _: AbstractCreature? ->
            this@QuickPick.loseDuration(1)
        }

    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.QuickPick()
        if (upgraded) c.upgrade()
        return c
    }
}