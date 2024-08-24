package rogue.power.weapon

import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponPowerCard
import rogue.characters.Rogue
import utils.makeId
import utils.useSneakAttack

class AssassinsBlade(
    damage: Int = 3,
    durability: Int = 5,
    magic: Int = 4,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = AssassinsBlade::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    magic = magic,
    upgraded = upgraded
) {
    init {
        damageModifier.cbOfOnAttacksToChangeDamage.add { _: DamageInfo?, damageAmount: Int, target: AbstractCreature? ->
            var d = damageAmount
            if (target is AbstractMonster) {
                useSneakAttack(target) {
                    d += magic
                }
            }
            d
        }
    }

    override fun onDestroy() {
        Rogue.onSneakAttack.removeIf { it.id == AssassinsBlade::class.makeId() }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.AssassinsBlade()
        if (upgraded) c.upgrade()
        return c
    }
}