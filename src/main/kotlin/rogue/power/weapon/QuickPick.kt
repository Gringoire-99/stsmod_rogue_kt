package rogue.power.weapon

import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.cards.AbstractWeaponPowerCard
import utils.drawCard

class QuickPick(
    damage: Int = 2,
    durability: Int = 6,
    magic: Int = 1,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = QuickPick::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    upgraded = upgraded,
    magic = magic
) {
    override var tempLoseDurability: Int = 0
        set(value) {
            if (value > field) {
                drawCard(magic)
            }
            field = value
        }

    init {
        damageModifier.cbOfOnAttack.add { _: DamageInfo?, _: Int, _: AbstractCreature? ->
            this@QuickPick.loseDurability(1)
        }

    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.QuickPick()
        if (upgraded) c.upgrade()
        return c
    }
}