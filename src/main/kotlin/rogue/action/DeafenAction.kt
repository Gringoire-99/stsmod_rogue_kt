package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import rogue.power.SilencePower

class DeafenAction(private val s: AbstractCreature, val m: AbstractMonster) : AbstractGameAction() {
    override fun update() {
        val buff = ArrayList(m.powers.filter {
            it.type == PowerType.BUFF
        })
        m.powers.removeIf {
            it.type == PowerType.BUFF
        }
        addToBot(ApplyPowerAction(m, s, SilencePower(m, buff)))
        isDone = true
    }
}