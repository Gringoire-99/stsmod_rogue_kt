package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.action.EmptyAction
import rogue.power.IAbstractPower
import rogue.power.common.ShadowSecretPower
import utils.makeId
import utils.modId

abstract class AbstractSecretPower(rawName: String, owner: AbstractCreature) :
    IAbstractPower(
        powerName = rawName,
        owner = owner,
        imgPath = "$modId/powers/${AbstractSecretPower::class.simpleName.toString()}",
        amount = -1
    ), NonStackablePower {

    abstract fun effect()

    fun triggerEffect() {
        effect()
        val shadowSecret = owner.getPower(ShadowSecretPower::class.makeId())
        if (shadowSecret != null) {
            shadowSecret.flash()
            repeat(shadowSecret.amount) {
                addToTop(EmptyAction {
                    owner.powers.forEach {
                        if (it is AbstractSecretPower && it !== this) {
                            it.effect()
                        }
                    }
                })
            }
        }
    }
}