package rogue.power.quest

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.cards.power.CrystalCore
import utils.isCavernCard

class TheCavernsBelowPower(owner: AbstractCreature) :
    AbstractQuestPower(rawName = TheCavernsBelowPower::class.simpleName.toString(), owner = owner) {
    override val maxCount: Int = 6
    override fun onComplete() {
        addToBot(MakeTempCardInHandAction(CrystalCore()))
    }

    private val playedCards = hashMapOf<String, Int>()
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (this.isCavernCard()) {
                val count = playedCards.getOrPut(cardID) { 0 } + 1
                playedCards[cardID] = count
                questCount = playedCards.values.maxOrNull() ?: 0
            }

        }
    }
}