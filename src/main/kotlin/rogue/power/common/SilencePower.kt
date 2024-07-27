package rogue.power

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower

class SilencePower(owner: AbstractCreature, private val removedPower: ArrayList<AbstractPower>) :
    IAbstractPower(
        powerName = SilencePower::class.simpleName.toString(),
        owner = owner,
        type = PowerType.DEBUFF,
        amount = -1
    ),
    NonStackablePower {
    override fun atEndOfRound() {
        removedPower.forEach {
            addToBot(ApplyPowerAction(owner, owner, it))
        }
        addToBot(RemoveSpecificPowerAction(owner, owner, this))
    }
}