package rogue.power.hero

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.action.GetUniqueCardAction
import rogue.cards.skill.ShadowReflection
import rogue.power.common.StealthPower

class DeathsShadowPower(owner: AbstractPlayer) :
    AbstractHeroPower(powerName = DeathsShadowPower::class.simpleName.toString(), owner = owner) {
    override val ability: () -> Unit = {
        (owner as? AbstractPlayer)?.apply {
            addToTop(GetUniqueCardAction(ShadowReflection()))
        }
    }
    override val afterApply = {
        addToBot(ApplyPowerAction(owner, owner, StealthPower(owner)))
        getBaseAbility()?.startOfTurn = ability
    }

}