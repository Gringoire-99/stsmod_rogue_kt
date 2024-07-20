package rogue.power.common

import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.power.IAbstractPower

class JadeGrowth(owner: AbstractCreature, amount: Int = 1) :
    IAbstractPower(powerName = JadeGrowth::class.simpleName.toString(), owner = owner, amount = amount) {


}