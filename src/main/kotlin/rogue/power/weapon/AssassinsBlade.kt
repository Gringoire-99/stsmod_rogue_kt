package rogue.power.weapon

import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponPowerCard
import utils.isAttackIntent

class AssassinsBlade(
    damage: Int = 3,
    duration: Int = 5,
    magic: Int = 4,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = AssassinsBlade::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    magic = magic,
    upgraded = upgraded
) {
    init {
        damageModifier.cbOfOnAttacksToChangeDamage.add { _: DamageInfo?, damageAmount: Int, target: AbstractCreature? ->
            var d = damageAmount
            if (target is AbstractMonster) {
                target.apply {
                    if (!target.intent.isAttackIntent()) {
                        d += magic
                    }
                }
            }
            d
        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.AssassinsBlade()
        if (upgraded) c.upgrade()
        return c
    }
}