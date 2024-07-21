package rogue.cards

import com.megacrit.cardcrawl.cards.AbstractCard

interface OnMakeTempCard {
    fun onMakeTempCard(c: AbstractCard)
}