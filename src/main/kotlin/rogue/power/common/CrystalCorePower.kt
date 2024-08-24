package rogue.power.common

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.OnAddCard
import rogue.patchs.CrystalCorePatch.Fields.isCrystalCoreEnhanced
import rogue.power.IAbstractPower
import utils.upBase

class CrystalCorePower(owner: AbstractPlayer, stackAmount: Int = 3) :
    IAbstractPower(powerName = CrystalCorePower::class.simpleName.toString(), owner = owner, amount = stackAmount),
    OnAddCard {

    override fun onAddCard(c: AbstractCard) {
        if (!isCrystalCoreEnhanced.get(c)) {
            flash()
            isCrystalCoreEnhanced.set(c, true)
            c.upBase(this@CrystalCorePower.amount)
        }
    }

    init {
        updateDescription()
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(amount)
        name = powerString.NAME
    }
}