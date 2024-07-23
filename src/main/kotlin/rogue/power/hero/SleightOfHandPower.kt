package rogue.power.hero

import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.action.ReduceAllCostInHandAction
import kotlin.math.min

class SleightOfHandPower(owner: AbstractPlayer) :
    AbstractHeroPower(powerName = SleightOfHandPower::class.simpleName.toString(), owner = owner, amount = 1) {
    override val ability = {
        if (amount > 0) {
            amount--
            addToBot(ReduceAllCostInHandAction())
        }
    }

    override fun atStartOfTurnPostDraw() {
        amount = min(2, amount + 1)
    }

    override val afterApply = {
        getBaseAbility()?.rightClick = ability
    }
}