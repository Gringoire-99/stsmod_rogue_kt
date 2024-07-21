package rogue.power.common

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.power.IAbstractPower
import utils.dealDamage
import utils.makeId
import kotlin.random.Random

class TheLobotomizerPower(owner: AbstractMonster, amount: Int = 1) :
    IAbstractPower(
        powerName = TheLobotomizerPower::class.simpleName.toString(),
        owner = owner,
        amount = amount,
        type = PowerType.DEBUFF
    ) {
    init {

        effect()
    }

    private fun effect() {
        if (owner is AbstractMonster) {
            val amount = (owner.getPower(TheLobotomizerPower::class.makeId())?.amount ?: 0) + this.amount
            if (amount < 0) {
                return
            }
            if (amount <= 33) {
                dealDamage(owner, owner, 3)
            } else if (amount <= 66) {
                val b = Random.nextBoolean()
                if (b) {
                    dealDamage(owner, owner, 3)
                } else {
                    addToTop(RemoveSpecificPowerAction(owner, owner, this.ID))
                    addToTop(RollMoveAction(owner as AbstractMonster))
                }
            } else if (amount <= 99) {
                val b = Random.nextBoolean()
                val c = Random.nextBoolean()
                if (b || c) {
                    dealDamage(owner, owner, 3)
                } else {
                    addToTop(RemoveSpecificPowerAction(owner, owner, this.ID))
                    addToTop(RollMoveAction(owner as AbstractMonster))
                }
            } else {
                if (owner is AbstractMonster) {
                    addToTop(StunMonsterAction(owner as AbstractMonster, owner))
                    addToTop(RemoveSpecificPowerAction(owner, owner, this.ID))
                }
            }
        }

    }

}