package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.weapon.AbstractWeaponPower
import utils.getWeaponPower

class EquipWeaponAction(val new: AbstractWeaponPower) : AbstractGameAction() {
    override fun update() {
//        检查是否有武器装备
        val old = AbstractDungeon.player.getWeaponPower()
        if (old != null) {
            old.onRemove()
            AbstractDungeon.player.powers.remove(old)
        }
        AbstractDungeon.player.powers.add(new)
        new.updateCard()
        isDone = true
    }
}