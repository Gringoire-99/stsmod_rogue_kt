package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class GetUniqueCardAction(val card: AbstractCard) : AbstractGameAction() {
    override fun update() {
        val find = AbstractDungeon.player.hand.group.find {
            it.cardID == card.cardID
        }
        if (find == null) {
            addToTop(MakeTempCardInHandAction(card))
        }
        isDone = true
    }
}