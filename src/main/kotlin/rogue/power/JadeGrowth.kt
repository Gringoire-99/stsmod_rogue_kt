package rogue.power

import com.megacrit.cardcrawl.core.AbstractCreature

class JadeGrowth(owner: AbstractCreature, amount: Int = 1) :
    IAbstractPower(powerName = JadeGrowth::class.simpleName.toString(), owner = owner, amount = amount) {
    init {
        updateDescription()
    }


}