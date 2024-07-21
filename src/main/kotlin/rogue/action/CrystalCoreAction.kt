package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import utils.upBase

class CrystalCoreAction(val magic: Int) : AbstractGameAction() {
    override fun update() {
        val player = AbstractDungeon.player
        player?.apply {
            hand.group.forEach {
                it.upBase(magic)
            }
            discardPile.group.forEach {
                it.upBase(magic)
            }
            drawPile.group.forEach {
                it.upBase(magic)
            }
        }
        isDone = true
    }
}