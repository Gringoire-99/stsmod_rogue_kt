package rogue.cards

import com.megacrit.cardcrawl.cards.AbstractCard

interface OnAfterCardPlayed {
    fun onAfterCardPlayed(otherCard: AbstractCard) {}
}