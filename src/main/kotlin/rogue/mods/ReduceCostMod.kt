package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import rogue.action.RemoveReduceCostModsAction
import utils.makeId
import kotlin.math.max
import kotlin.math.min

class ReduceCostMod(
    var amount: Int = 1,
    private val removeOnPlay: Boolean = false,
    private val removeOnOtherCardPlayed: Boolean = false,
    private val removeOnEndOfTurn: Boolean = false,
    private val isTurnEffect: Boolean = false,
    val applyId: String = id
) : AbstractCardModifier() {
    private var reduceCost = 0

    companion object {
        val id = ReduceCostMod::class.makeId()
    }

    override fun onInitialApplication(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                reduceCost = max(0, min(cost, amount))
                updateCost(-amount)
            } else {
                reduceCost = max(0, min(costForTurn, amount))
                costForTurn = max(0, costForTurn - reduceCost)
            }
            isCostModified = true
        }
    }

    override fun shouldApply(card: AbstractCard?): Boolean {
        val shouldApply = card != null && card.cost >= 0
        return shouldApply
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                updateCost(reduceCost)
            } else {
                costForTurn += reduceCost
            }
            isCostModified = false
        }
    }

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return removeOnPlay
    }

    override fun onOtherCardPlayed(card: AbstractCard?, otherCard: AbstractCard?, group: CardGroup?) {
        if (removeOnOtherCardPlayed) {
            addToTop(RemoveReduceCostModsAction(applyId))
        }
    }

    override fun removeAtEndOfTurn(card: AbstractCard?): Boolean {
        return removeOnEndOfTurn
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }

    override fun makeCopy(): AbstractCardModifier {
        return ReduceCostMod()
    }

}