package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.RetainPower
import kotlin.math.max

class SecretPassageAction(val p: AbstractPlayer = AbstractDungeon.player, val upgraded: Boolean = false, val draw: Int = 6) :
    AbstractGameAction() {
    override fun update() {
        val group = p.hand.group
        val retainPower = RetainPower(p, group)
        val drawNumber = if (upgraded) max(group.size, draw) else draw
        p.hand.group = arrayListOf()
        addToTop(DrawCardAction(drawNumber))
        addToTop(ApplyPowerAction(p, p, retainPower, 1))
        isDone = true
    }
}