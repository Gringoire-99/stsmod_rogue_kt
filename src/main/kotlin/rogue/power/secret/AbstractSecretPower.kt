package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.power.IAbstractPower
import utils.modId

abstract class AbstractSecretPower(rawName: String, owner: AbstractCreature) :
    IAbstractPower(
        powerName = rawName,
        owner = owner,
        imgPath = "$modId/powers/${AbstractSecretPower::class.simpleName.toString()}",
        amount = -1
    ), NonStackablePower