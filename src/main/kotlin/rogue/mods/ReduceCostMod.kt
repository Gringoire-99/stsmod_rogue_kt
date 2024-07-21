package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import utils.makeId
import kotlin.math.max

class ReduceCostMod(
    val amount: Int = 1,
    val removeOnPlay: Boolean = false,
    val removeOnOtherCardPlayed: Boolean = false,
    val removeOnEndOfTurn: Boolean = false,
    val isTurnEffect: Boolean = false
) : AbstractCardModifier() {
    companion object {
        val id = ReduceCostMod::class.makeId()
    }

    override fun onInitialApplication(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                updateCost(-amount)
            } else {
                costForTurn = max(0, costForTurn - amount)
            }
        }
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            if (!isTurnEffect) {
                updateCost(amount)
            } else {
                costForTurn += amount
            }
        }
    }

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return removeOnPlay
    }

    override fun onOtherCardPlayed(card: AbstractCard?, otherCard: AbstractCard?, group: CardGroup?) {
        if (removeOnOtherCardPlayed) {
            CardModifierManager.removeModifiersById(card, id, true)
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