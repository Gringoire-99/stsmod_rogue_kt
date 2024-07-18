package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.skill.Cheatsheet
import utils.makeId

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
        val index = (owner as AbstractPlayer).hand.group.find { it.cardID == Cheatsheet::class.makeId() }
        if (index == null) {
            val cheatsheet = Cheatsheet()
            if (upgraded) {
                cheatsheet.upgrade()
            }
            addToBot(MakeTempCardInHandAction(cheatsheet))
        }
    }
}