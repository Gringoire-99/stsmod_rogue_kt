package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.patchs.CrystalCorePatch
import utils.upBase

class CrystalCoreAction(val magic: Int) : AbstractGameAction() {
    override fun update() {
        val player = AbstractDungeon.player
        player?.apply {
            arrayOf(hand.group, discardPile.group, drawPile.group, exhaustPile.group, limbo.group).forEach {
                it.forEach { c ->
                    CrystalCorePatch.Fields.isCrystalCoreEnhanced.set(c, true)
                    c.upBase(magic)
                }
            }

        }
        isDone = true
    }
}