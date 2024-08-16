package rogue.power.secret

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.action.GetUniqueCardAction
import rogue.cards.skill.Cheatsheet

class PlagiarizePower(owner: AbstractPlayer, val upgraded: Boolean = false) :
    AbstractSecretPower(rawName = PlagiarizePower::class.simpleName.toString(), owner = owner) {
    init {
        updateDescription()
        val cheatsheet = Cheatsheet()
        if (upgraded) {
            cheatsheet.upgrade()
        }
        addToBot(MakeTempCardInHandAction(cheatsheet))
    }

    override fun effect() {
        val cheatsheet = Cheatsheet()
        if (upgraded) {
            cheatsheet.upgrade()
        }
        addToBot(GetUniqueCardAction(cheatsheet))
        flash()
    }

    override fun atStartOfTurnPostDraw() {
        triggerEffect()
    }
}