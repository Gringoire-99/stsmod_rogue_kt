package rogue.power.secret

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.PoisonPower
import rogue.action.EmptyAction
import utils.dealDamage
import utils.isAlive

private const val require = 1

class StickySituationPower(owner: AbstractPlayer, val magic: Int = 2) :
    AbstractSecretPower(rawName = StickySituationPower::class.simpleName.toString(), owner = owner) {
    init {
        updateDescription()
    }

    override fun atEndOfRound() {
        addToTop(EmptyAction {
            val any = AbstractDungeon.getMonsters().monsters.any { m ->
                m.isAlive() && m.powers.filter { it.type == PowerType.DEBUFF }.size > require
            }
            if (any) {
                triggerEffect()
            }
        })
    }

    override fun effect() {
        addToTop(EmptyAction {
            AbstractDungeon.getMonsters().monsters.forEach { m ->
                if (m.isAlive()) {
                    val count = m.powers.filter { it.type == PowerType.DEBUFF }.size
                    if (count > require) {
                        val d = count * magic
                        addToTop(ApplyPowerAction(m, owner, PoisonPower(m, owner, 2), 2))
                        dealDamage(owner, m, d, damageEffect = AbstractGameAction.AttackEffect.POISON)
                    }
                }
            }
        })
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magic)
        name = powerString.NAME
    }

}