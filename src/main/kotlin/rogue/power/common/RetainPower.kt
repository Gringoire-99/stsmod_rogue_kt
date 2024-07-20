package rogue.power.common

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.IAbstractPower

class RetainPower(owner: AbstractPlayer = AbstractDungeon.player, val cards: ArrayList<AbstractCard>) :
    IAbstractPower(powerName = RetainPower::class.simpleName.toString(), owner = owner), NonStackablePower {

    override fun atStartOfTurnPostDraw() {
        cards.forEach {
            addToTop(MakeTempCardInHandAction(it))
        }
        addToBot(RemoveSpecificPowerAction(owner, owner, this))
    }

}