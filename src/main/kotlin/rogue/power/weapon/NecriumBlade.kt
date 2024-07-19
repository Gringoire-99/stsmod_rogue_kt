package rogue.power.weapon

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.cards.AbstractWeaponPowerCard
import rogue.mods.NecriumMod
import utils.addMod

class NecriumBlade(
    damage: Int = 3,
    duration: Int = 3,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = NecriumBlade::class.simpleName.toString(),
    damage = damage,
    duration = duration,
    upgraded = upgraded
) {
    init {
        damageModifier.cbOfOnAttack.add { _: DamageInfo?, _: Int, _: AbstractCreature? ->
            addToTop(
                SelectCardsAction(
                    AbstractDungeon.player.discardPile.group,
                    1,
                    "选择一张卡片赋予死金",
                    true,
                    { true }) { cards ->
                    cards.forEach {
                        it.addMod(NecriumMod())
                    }
                })
            loseDuration(1)

        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.NecriumBlade()
        if (upgraded) c.upgrade()
        return c
    }

}