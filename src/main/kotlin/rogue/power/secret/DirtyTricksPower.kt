package rogue.power.secret

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.VulnerablePower
import com.megacrit.cardcrawl.powers.WeakPower
import rogue.action.EmptyAction
import utils.canSneakAttack
import utils.isAlive
import utils.useSneakAttack

class DirtyTricksPower(owner: AbstractPlayer, val magicNumber: Int = 1) :
    AbstractSecretPower(rawName = DirtyTricksPower::class.simpleName.toString(), owner = owner) {
    init {
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        val targets = AbstractDungeon.getMonsters().monsters.filter { it.isAlive() && canSneakAttack(it) }
        if (targets.isNotEmpty()) {
            triggerEffect()
        }
    }

    override fun effect() {
        var count = 0
        addToTop(EmptyAction {
            AbstractDungeon.getMonsters().monsters.forEach {
                if (it.isAlive()) {
                    useSneakAttack(it) {
                        count++
                    }
                }
            }
            repeat(count) {
                addToTop(EmptyAction {
                    AbstractDungeon.getMonsters().monsters.forEach {
                        addToBot(ApplyPowerAction(it, owner, WeakPower(it, magicNumber, false), magicNumber))
                        addToBot(ApplyPowerAction(it, owner, VulnerablePower(it, magicNumber, false), magicNumber))
                    }
                })
                flash()
            }
        })
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }

}
