package rogue.power.secret

import com.megacrit.cardcrawl.characters.AbstractPlayer

class PerjuryPower(owner: AbstractPlayer, val magic: Int = 2) :
    AbstractSecretPower(PerjuryPower::class.simpleName.toString(), owner) {
    init {
        updateDescription()
    }

    override fun effect() {
        if (owner is AbstractPlayer) {
            val takes =
                owner.powers.filter { it is AbstractSecretPower && it !is PerjuryPower }.shuffled()
                    .take(magic)
            takes.forEach {
                (it as? AbstractSecretPower)?.apply {
                    effect()
                    flash()
                }
            }
        }
    }


    override fun atEndOfTurnPreEndTurnCards(isPlayer: Boolean) {
        if (isPlayer) {
            triggerEffect()
        }
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magic)
        name = powerString.NAME
    }
}