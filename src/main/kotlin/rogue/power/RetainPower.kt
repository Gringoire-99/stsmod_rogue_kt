package rogue.power

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class RetainPower(owner: AbstractPlayer = AbstractDungeon.player, val cards: ArrayList<AbstractCard>) :
    IAbstractPower(powerName = RetainPower::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        (owner as AbstractPlayer).hand.group.addAll(cards)
        addToBot(RemoveSpecificPowerAction(owner, owner, this))
    }

}