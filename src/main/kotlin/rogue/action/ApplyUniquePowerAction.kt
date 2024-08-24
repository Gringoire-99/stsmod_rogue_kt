package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower

class ApplyUniquePowerAction(
    val power: AbstractPower,
    val owner: AbstractCreature = power.owner,
    val amount: Int = power.amount,
    val cb: () -> Unit = {}
) :
    AbstractGameAction() {
    override fun update() {
        addToTop(RemoveSpecificPowerAction(owner, owner, power.ID))
        addToBot(ApplyPowerAction(owner, owner, power, amount))
        addToBot(EmptyAction {
            cb()
        })
        isDone = true
    }
}