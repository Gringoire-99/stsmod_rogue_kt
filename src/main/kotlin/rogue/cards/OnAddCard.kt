package rogue.cards

import com.megacrit.cardcrawl.cards.AbstractCard

interface OnAddCard {
    fun onAddCard(c: AbstractCard)
}