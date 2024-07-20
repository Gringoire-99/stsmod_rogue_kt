package rogue.action

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import utils.addMod

class CopyHandAction(val p: AbstractPlayer) : AbstractGameAction() {
    override fun update() {
        val group = p.hand.group
        group.forEach {
            it.addMod(ExhaustMod(), EtherealMod())
            addToBot(MakeTempCardInHandAction(it))
        }
        isDone = true
    }
}