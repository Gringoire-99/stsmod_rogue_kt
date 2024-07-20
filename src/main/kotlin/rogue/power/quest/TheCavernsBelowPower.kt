package rogue.power.quest

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.cards.skill.CrystalCore

class TheCavernsBelowPower(owner: AbstractCreature) :
    AbstractQuestPower(rawName = TheCavernsBelowPower::class.simpleName.toString(), owner = owner) {
    override val maxCount: Int = 8
    override fun onComplete() {
        addToBot(MakeTempCardInHandAction(CrystalCore()))
    }

    private val playedCards = hashMapOf<String, Int>()
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (this@apply.color != CardColor.COLORLESS
                && CardTags.STARTER_DEFEND !in this@apply.tags
                && CardTags.STARTER_STRIKE !in this@apply.tags
            ) {
                val count = playedCards.getOrPut(cardID) { 0 } + 1
                playedCards[cardID] = count
                questCount = playedCards.values.maxOrNull() ?: 0
                amount = questCount
            }

        }
    }
}