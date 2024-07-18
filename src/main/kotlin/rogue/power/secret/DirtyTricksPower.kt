package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import utils.drawCard
import utils.isAttackIntent

class DirtyTricksPower(owner: AbstractPlayer, val magicNumber: Int = 2) :
    AbstractSecretPower(rawName = DirtyTricksPower::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        updateDescription()
    }


    override fun atStartOfTurnPostDraw() {

        var count = 0
        AbstractDungeon.getMonsters().monsters.forEach {
            if (!it.intent.isAttackIntent()) {
                count += magicNumber
            }
        }
        drawCard(count)
        flash()
    }


    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }

}
