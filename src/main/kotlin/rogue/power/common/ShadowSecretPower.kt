package rogue.power.common

import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.power.IAbstractPower

class ShadowSecretPower(owner: AbstractCreature, val stackAmount: Int = 1) :
    IAbstractPower(powerName = ShadowSecretPower::class.simpleName.toString(), owner = owner, amount = stackAmount) {
    init {
        updateDescription()
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(amount)
        name = powerString.NAME
    }
}