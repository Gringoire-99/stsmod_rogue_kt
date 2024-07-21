package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.power.hero.AbstractHeroPower

class ApplyHeroPowerAction(val p: AbstractPlayer, val power: AbstractHeroPower) : AbstractGameAction() {
    override fun update() {
        val find = p.powers.find { it is AbstractHeroPower } as AbstractHeroPower?
        if (find != null) {
            addToTop(RemoveSpecificPowerAction(p, p, find))
        }
        addToBot(ApplyPowerAction(p, p, power))
        addToBot(EmptyAction(power.afterApply))
        isDone = true
    }
}