package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import rogue.power.hero.AbstractHeroPower

class ApplyHeroPowerAction(val power: AbstractHeroPower) : AbstractGameAction() {
    override fun update() {
        addToBot(ApplyUniquePowerAction(power) {
            power.afterApply()
        })
        isDone = true
    }
}