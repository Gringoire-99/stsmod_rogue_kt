package rogue.power.secret

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.DexterityPower
import rogue.power.common.StealthPower
import utils.gainBlock
import utils.makeId

class EvasionPower(owner: AbstractPlayer, val magicNumber: Int = 9) :
    AbstractSecretPower(rawName = EvasionPower::class.simpleName.toString(), owner = owner) {

    init {
        updateDescription()
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            if (owner.isPlayer) {
                (owner as AbstractPlayer).apply {
                    if (owner.currentBlock == 0 || owner.hasPower(StealthPower::class.makeId())) {
                        val power: AbstractPower? = getPower(DexterityPower.POWER_ID)
                        val amount = power?.amount ?: 0
                        gainBlock(owner, magicNumber + amount)
                        flash()
                    }
                }
            }
        }
    }


    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }

}