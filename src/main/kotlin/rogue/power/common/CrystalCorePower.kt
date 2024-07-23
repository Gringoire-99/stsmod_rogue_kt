package rogue.power.common

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.OnMakeTempCard
import rogue.power.IAbstractPower
import utils.upBlock
import utils.upDamage
import utils.upMagicNumber

class CrystalCorePower(owner: AbstractPlayer, val magic: Int = 3) :
    IAbstractPower(powerName = CrystalCorePower::class.simpleName.toString(), owner = owner, amount = -1),
    NonStackablePower, OnMakeTempCard {


    override fun onMakeTempCard(c: AbstractCard) {
        flash()
        c.apply {
            upDamage(magic)
            upBlock(magic)
            upMagicNumber(magic)
        }
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magic)
        name = powerString.NAME
    }
}