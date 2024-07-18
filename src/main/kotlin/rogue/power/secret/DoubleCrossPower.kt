package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import utils.drawCard


class DoubleCrossPower(owner: AbstractPlayer, val magicNumber: Int = 1) :
    AbstractSecretPower(rawName = DoubleCrossPower::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        updateDescription()
    }


    override fun atStartOfTurnPostDraw() {
        if (AbstractDungeon.getMonsters().monsters.size == 1) {
            drawCard(magicNumber)
            flash()
        }
    }
    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }
}