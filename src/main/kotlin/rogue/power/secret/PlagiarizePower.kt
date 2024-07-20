package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.action.GetUniqueCardAction
import rogue.cards.skill.Cheatsheet

class PlagiarizePower(owner: AbstractPlayer, val upgraded: Boolean = false) :
    AbstractSecretPower(rawName = PlagiarizePower::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        updateDescription()
        val cheatsheet = Cheatsheet()
        if (upgraded) {
            cheatsheet.upgrade()
        }
        addToBot(MakeTempCardInHandAction(cheatsheet))
    }

    override fun atStartOfTurnPostDraw() {

        val cheatsheet = Cheatsheet()
        if (upgraded) {
            cheatsheet.upgrade()
        }
        GetUniqueCardAction(cheatsheet)
    }
}