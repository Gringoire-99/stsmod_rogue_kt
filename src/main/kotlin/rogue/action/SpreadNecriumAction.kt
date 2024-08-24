package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.mods.NecriumMod
import rogue.mods.SpreadNecriumMod
import utils.Rogue_Color
import utils.addMod

class SpreadNecriumAction : AbstractGameAction() {
    override fun update() {
        val group = AbstractDungeon.player.hand.group
        val random = group.randomOrNull()
        random?.addMod(NecriumMod(), SpreadNecriumMod())
        random?.glowColor = Rogue_Color.cpy()
        random?.beginGlowing()
        isDone = true
    }
}