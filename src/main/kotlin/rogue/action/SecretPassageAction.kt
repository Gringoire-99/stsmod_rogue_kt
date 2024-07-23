package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.common.RetainPower

class SecretPassageAction(val p: AbstractPlayer = AbstractDungeon.player, val draw: Int = 6) :
    AbstractGameAction() {
    override fun update() {
        val group = p.hand.group
        val retainPower = RetainPower(p, group)
        p.hand.group = arrayListOf()
        addToTop(DrawCardAction(draw))
        addToTop(ApplyPowerAction(p, p, retainPower, 1))
        isDone = true
    }
}