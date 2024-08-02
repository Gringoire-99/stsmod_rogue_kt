package rogue.action

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DrawCardFromDiscardPileAction(val cb: (card: AbstractCard) -> Unit = {}) : AbstractGameAction() {
    override fun update() {
        val last = AbstractDungeon.player.discardPile.group.lastOrNull()
        if (last != null) {
            cb(last)
            addToTop(
                MoveCardsAction(
                    AbstractDungeon.player.hand,
                    AbstractDungeon.player.discardPile,
                    { c -> c == last }, {})
            )
        }
        isDone = true
    }
}