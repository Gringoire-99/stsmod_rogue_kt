package rogue.power.weapon

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponPowerCard
import rogue.power.TheLobotomizerPower

class TheLobotomizer(
    damage: Int = 3,
    duration: Int = 2,
    upgraded: Boolean = false,
    magic: Int = 4
) : AbstractWeaponPower(
    rawName = TheLobotomizer::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded,
    magic = magic
) {
    init {

        damageModifier.cbOfOnLastDamageTakenUpdate.add { _: DamageInfo?,
                                                         lastDamageTaken: Int,
                                                         _: Int,
                                                         target: AbstractCreature? ->
            if (lastDamageTaken > 0) {
                (target as AbstractMonster).apply {
                    addToBot(ApplyPowerAction(this, owner, TheLobotomizerPower(this, magic), magic))
                }
            }
        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.TheLobotomizer()
        if (upgraded) c.upgrade()
        return c
    }
}