package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PersistFields
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import utils.makeId

class PersistMod(val amount: Int = 1, private val removeOnEndOfTurn: Boolean = false) : AbstractCardModifier() {
    override fun onInitialApplication(card: AbstractCard?) {
        PersistFields.setBaseValue(card, amount)
    }

    companion object {
        val id = PersistMod::class.makeId()
    }

    private val cardString: CardStrings = CardCrawlGame.languagePack.getCardStrings(NecriumMod.id)
    private val s: String = cardString.DESCRIPTION
    override fun shouldApply(card: AbstractCard?): Boolean {
        return !CardModifierManager.hasModifier(card, id)
    }

    override fun removeAtEndOfTurn(card: AbstractCard?): Boolean {
        val p = PersistFields.persist.get(card)
        return removeOnEndOfTurn || p == -1
    }

    override fun onRemove(card: AbstractCard?) {
        PersistFields.setBaseValue(card, -1)
        card?.apply {
            rawDescription = rawDescription.replace(s, "")
            initializeDescription()
        }
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription ${s}"
    }

    override fun makeCopy(): AbstractCardModifier {
        return PersistMod()
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }
}