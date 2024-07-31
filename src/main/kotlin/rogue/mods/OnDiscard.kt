package rogue.mods

import com.megacrit.cardcrawl.cards.AbstractCard

interface OnDiscard {
    fun onManualDiscard(target: AbstractCard)
}