package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect

class AddCardInDiscardPile(val card: AbstractCard) : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.effectList.add(ShowCardAndAddToDiscardEffect(card))
        addToBot(DiscardSpecificCardAction(card))
        isDone = true
    }
}