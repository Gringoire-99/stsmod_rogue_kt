package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.characters.Rogue
import utils.addToQueue
import utils.generateCardChoices

class ChaoticTendrilAction(val count: Int = Rogue.chaoticTendrilCount, val m: AbstractMonster) :
    AbstractGameAction() {
    override fun update() {
        play(count)
        isDone = true
    }

    fun play(total: Int) {
        var t = total
        val filter = CardFilter(
            cardType = hashSetOf(AbstractCard.CardType.ATTACK),
            cardRarity = hashSetOf(
                AbstractCard.CardRarity.SPECIAL,
                AbstractCard.CardRarity.BASIC,
                AbstractCard.CardRarity.COMMON,
                AbstractCard.CardRarity.UNCOMMON,
                AbstractCard.CardRarity.RARE,
                AbstractCard.CardRarity.CURSE
            ),
            excludeColor = hashSetOf(),
            cost = { it in 1..t },
            excludeTags = hashSetOf()
        )
        val choices = generateCardChoices(cardFilter = filter, number = 50, isOtherClassCard = false)
        choices.forEach {
            if (t <= 0) {
                return@play
            } else if (it.cost <= t) {
                t -= it.cost
                addToQueue(it, m, purge = true)
            }
        }
        if (total > 0) {
            play(t)
        }
    }
}