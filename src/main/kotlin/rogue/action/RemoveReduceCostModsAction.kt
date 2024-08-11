package rogue.action

import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.mods.ReduceCostMod

class RemoveReduceCostModsAction(val id: String = ReduceCostMod.id) : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.player.hand.group.forEach { c ->
            CardModifierManager.modifiers(c).forEach { m ->
                if (m is ReduceCostMod && m.applyId == id) {
                    addToTop(EmptyAction {
                        CardModifierManager.removeSpecificModifier(c, m, true)
                    })
                }
            }
        }
        isDone = true
    }
}