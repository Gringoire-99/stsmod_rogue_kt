package rogue.power.weapon

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.VulnerablePower
import rogue.cards.AbstractWeaponPowerCard

class PerditionsBlade(
    damage: Int = 3, duration: Int = 3, magic: Int = 4, upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = PerditionsBlade::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    magic = magic,
    upgraded = upgraded
) {
    init {
        damageModifier.cbOfOnAttack.add { _: DamageInfo?, _: Int, target: AbstractCreature? ->
            target?.apply {
                val p = getPower(VulnerablePower.POWER_ID)
                if (p != null) {
                    addToTop(
                        DamageAction(
                            target,
                            DamageInfo(p.owner, magic, DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.FIRE
                        )
                    )
                } else {
                    addToTop(ApplyPowerAction(target, AbstractDungeon.player, VulnerablePower(target, 1, false), 1))
                }
            }
        }
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            loseDuration(1)
        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val perditionsBlade = rogue.cards.power.PerditionsBlade()
        if (upgraded) perditionsBlade.upgrade()
        return perditionsBlade
    }
}