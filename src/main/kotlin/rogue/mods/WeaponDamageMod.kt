package rogue.mods

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

typealias OnAttack = (info: DamageInfo?, damageAmount: Int, target: AbstractCreature?) -> Unit
typealias OnAttacksToChangeDamage = (info: DamageInfo?, damageAmount: Int, target: AbstractCreature?) -> Int
typealias OnLastDamageTakenUpdate = (
    info: DamageInfo?,
    lastDamageTaken: Int,
    overkillAmount: Int,
    target: AbstractCreature?
) -> Unit

class WeaponDamageMod : AbstractDamageModifier() {
    val cbOfOnAttack = arrayListOf<OnAttack>()
    val cbOfOnAttacksToChangeDamage =
        arrayListOf<OnAttacksToChangeDamage>()
    val cbOfOnLastDamageTakenUpdate = arrayListOf<OnLastDamageTakenUpdate>()


    override fun onAttackToChangeDamage(info: DamageInfo?, damageAmount: Int, target: AbstractCreature?): Int {
        var d = damageAmount
        cbOfOnAttacksToChangeDamage.forEach {
            d = it(info, d, target)
        }
        return d
    }

    override fun onAttack(info: DamageInfo?, damageAmount: Int, target: AbstractCreature?) {
        cbOfOnAttack.forEach {
            it(info, damageAmount, target)
        }
    }

    override fun onLastDamageTakenUpdate(
        info: DamageInfo?,
        lastDamageTaken: Int,
        overkillAmount: Int,
        target: AbstractCreature?
    ) {
        cbOfOnLastDamageTakenUpdate.forEach {
            it(info, lastDamageTaken, overkillAmount, target)
        }
    }

    override fun makeCopy(): AbstractDamageModifier {
        return WeaponDamageMod()
    }
}