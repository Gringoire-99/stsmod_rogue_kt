package rogue.action

import basemod.BaseMod
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import common.CardFilter
import kotlin.math.max

class DrawCardsAction(val amount: Int, val cardFilter: CardFilter) : AbstractGameAction() {
    override fun update() {
        if (amount > 0) {
            val drawPile = AbstractDungeon.player.drawPile.group
            val filtered = cardFilter.filter(drawPile).take(amount)
            val maxHS = BaseMod.MAX_HAND_SIZE
            val handSize = AbstractDungeon.player.hand.size()
            if (filtered.isNotEmpty()) {
                val canDraw = max(0, maxHS - handSize)
                addToHand(filtered.take(canDraw))
                val discard = max(0, filtered.size - canDraw)
                addToDiscardPile(filtered.takeLast(discard))
            }
        }

        isDone = true
    }

    private fun addToHand(cards: List<AbstractCard>) {
        if (cards.isNotEmpty()) {
            cards.forEach { card ->
                card.unhover()
                card.lighten(true)
                card.setAngle(0.0f)
                card.drawScale = 0.12f
                card.targetDrawScale = 0.75f
                card.current_x = CardGroup.DRAW_PILE_X
                card.current_y = CardGroup.DRAW_PILE_Y
                AbstractDungeon.player.drawPile.removeCard(card)
                AbstractDungeon.player.hand.addToTop(card)
                AbstractDungeon.player.hand.refreshHandLayout()
                AbstractDungeon.player.hand.applyPowers()
            }
        }
    }

    private fun addToDiscardPile(cards: List<AbstractCard>) {
        if (cards.isNotEmpty()) {
            AbstractDungeon.player.createHandIsFullDialog()
            cards.forEach {
                AbstractDungeon.player.drawPile.moveToDiscardPile(it)
            }
        }
    }
}