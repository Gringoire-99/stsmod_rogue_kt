package rogue.power

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.core.AbstractCreature

class SpiritOfTheSharkPower(owner: AbstractCreature) :
    IAbstractPower(powerName = SpiritOfTheSharkPower::class.simpleName.toString(), owner = owner, amount = -1),
    NonStackablePower