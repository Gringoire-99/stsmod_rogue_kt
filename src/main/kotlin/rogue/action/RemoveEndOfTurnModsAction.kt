package rogue.action

import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.mods.ReduceCostMod

class RemoveEndOfTurnModsAction : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.player.hand.group.forEach {
            CardModifierManager.removeModifiersById(it, ReduceCostMod.id, false)
        }
        isDone = true
    }
}