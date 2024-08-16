package rogue.power.secret

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.DexterityPower
import rogue.action.EmptyAction
import rogue.power.common.StealthPower
import utils.gainBlock
import utils.makeId

class EvasionPower(owner: AbstractPlayer, val magicNumber: Int = 9) :
    AbstractSecretPower(rawName = EvasionPower::class.simpleName.toString(), owner = owner) {

    init {
        updateDescription()
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer && owner is AbstractPlayer && (owner.currentBlock == 0 || owner.hasPower(StealthPower::class.makeId()))) {
            triggerEffect()
        }
    }

    override fun effect() {
        addToTop(EmptyAction {
            if (owner is AbstractPlayer && (owner.currentBlock == 0 || owner.hasPower(StealthPower::class.makeId()))) {
                val power: AbstractPower? = owner.getPower(DexterityPower.POWER_ID)
                val amount = power?.amount ?: 0
                gainBlock(owner, magicNumber + amount)
                flash()
            }
        })
    }


    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }

}