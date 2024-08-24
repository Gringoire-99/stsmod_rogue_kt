package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.weapon.AbstractWeaponPower

class EquipWeaponAction(private val new: AbstractWeaponPower, val onEquip: () -> Unit = {}) : AbstractGameAction() {
    override fun update() {
//        检查是否有武器装备
        AbstractDungeon.actionManager.addToBottom(
            RemoveSpecificPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                AbstractWeaponPower.id
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new
            )
        )
        addToBot(EmptyAction {
            onEquip()
        })

        isDone = true
    }
}