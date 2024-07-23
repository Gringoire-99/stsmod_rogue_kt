package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.mods.ReduceCostMod
import utils.addMod

class ReduceAllCostInHandAction : AbstractGameAction() {
    override fun update() {
        val group = AbstractDungeon.player.hand.group
        group.forEach {
            it.addMod(
                ReduceCostMod(
                    isTurnEffect = true,
                    removeOnPlay = true,
                    removeOnOtherCardPlayed = true,
                    removeOnEndOfTurn = true
                )
            )
        }
        isDone = true
    }
}