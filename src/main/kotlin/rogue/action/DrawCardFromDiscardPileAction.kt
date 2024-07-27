package rogue.action

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DrawCardFromDiscardPileAction(val cb: (card: AbstractCard) -> Unit = {}) : AbstractGameAction() {
    override fun update() {
        val topCard = AbstractDungeon.player.discardPile.topCard
        if (!AbstractDungeon.player.discardPile.isEmpty) {
            addToTop(
                MoveCardsAction(
                    AbstractDungeon.player.hand,
                    AbstractDungeon.player.discardPile,
                    { card: AbstractCard ->
                        card == topCard
                    },
                    { cards ->
                        cb(cards[0])
                    }
                ))
        }

        isDone = true
    }
}