package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import rogue.action.RemoveEndOfTurnModsAction
import utils.makeId
import kotlin.math.max
import kotlin.math.min

class ReduceCostMod(
    var amount: Int = 1,
    private val removeOnPlay: Boolean = false,
    private val removeOnOtherCardPlayed: Boolean = false,
    private val removeOnEndOfTurn: Boolean = false,
    private val isTurnEffect: Boolean = false
) : AbstractCardModifier() {
    var baseCost = 0

    companion object {
        val id = ReduceCostMod::class.makeId()
    }

    override fun onInitialApplication(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                baseCost = cost
                updateCost(-amount)
            } else {
                baseCost = costForTurn
                costForTurn = max(0, costForTurn - amount)
            }
            isCostModified = true
        }
    }

    override fun shouldApply(card: AbstractCard?): Boolean {
        val firstOrNull = CardModifierManager.modifiers(card).firstOrNull { it is ReduceCostMod } as ReduceCostMod?
        var shouldApply = card != null && card.cost >= 0
        if (firstOrNull != null && isTurnEffect) {
            card?.apply {
                costForTurn = max(0, costForTurn - amount)
                firstOrNull.amount++
                shouldApply = false
            }
        }
        return shouldApply
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                updateCost(amount)
            } else {
                costForTurn = min(baseCost, costForTurn + amount)
            }
            isCostModified = false
        }
    }

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return removeOnPlay
    }

    override fun onOtherCardPlayed(card: AbstractCard?, otherCard: AbstractCard?, group: CardGroup?) {
        if (removeOnOtherCardPlayed) {
            addToTop(RemoveEndOfTurnModsAction())
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