package rogue.power.secret

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import utils.addToQueue
import utils.isCavernCard

class ShadowClonePower(owner: AbstractCreature, private val max: Int = 2) :
    AbstractSecretPower(rawName = ShadowClonePower::class.simpleName.toString(), owner = owner) {
    init {
        updateDescription()
    }

    private val playedCards = hashMapOf<String, Int>()
    private var isTriggered = false
    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (this.isCavernCard()) {
                val count = playedCards.getOrPut(cardID) { 0 } + 1
                playedCards[cardID] = count
                val m = playedCards.values.maxOrNull() ?: 0
                if (m >= max && !isTriggered) {
                    isTriggered = true
                    triggerEffect()
                }
            }

        }
    }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        var a = playedCards.values.maxOrNull() ?: 0
        if (a >= max) a = max
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            "${a}/${max}",
            x,
            y,
            Color.GREEN.cpy()
        )

    }

    override fun effect() {
        val m = playedCards.values.maxOrNull() ?: 0
        val maxPlayedCard = getMaxPlayedCards(AbstractDungeon.actionManager.cardsPlayedThisCombat).randomOrNull()
        if (maxPlayedCard != null && m >= max) {
            addToQueue(
                maxPlayedCard.makeSameInstanceOf(),
                t = null,
                random = true,
                purge = true
            )
        }
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            playedCards.clear()
            isTriggered = false
        }
    }

    private fun getMaxPlayedCards(l: ArrayList<AbstractCard>, max: Int = 0): List<AbstractCard> {
        val set = hashSetOf<CardCount>()
        l.forEach { card ->
            val firstOrNull = set.firstOrNull { it.card.cardID == card.cardID }
            if (firstOrNull != null) {
                firstOrNull.count++
            } else if (card.isCavernCard()) {
                set.add(CardCount(1, card))
            }
        }
        if (set.isEmpty()) {
            return arrayListOf()
        }
        val m = set.maxOfOrNull { it.count } ?: 0
        if (m < max || m == 0) {
            return arrayListOf()
        }
        val filter = set.filter {
            it.count == m
        }.map {
            it.card
        }

        return filter
    }

    class CardCount(var count: Int, val card: AbstractCard) {
        override fun equals(other: Any?): Boolean {
            if (other !is CardCount) return false
            return card.cardID == other.card.cardID
        }

        override fun hashCode(): Int {
            var result = count
            result = 31 * result + card.hashCode()
            return result
        }
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(max)
        name = powerString.NAME
    }
}