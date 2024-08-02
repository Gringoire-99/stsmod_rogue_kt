package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import rogue.action.SpreadNecriumAction
import utils.makeId

class SpreadNecriumMod : AbstractCardModifier() {

    companion object {
        val id = SpreadNecriumMod::class.makeId()
    }

    private val cardString: CardStrings = CardCrawlGame.languagePack.getCardStrings(id)

    private val s: String = cardString.DESCRIPTION
    override fun onExhausted(card: AbstractCard?) {
        addToBot(SpreadNecriumAction())
    }

    override fun makeCopy(): AbstractCardModifier {
        return SpreadNecriumMod()
    }

    override fun shouldApply(card: AbstractCard?): Boolean {
        val modifiers = CardModifierManager.modifiers(card)
        return !modifiers.any { it is SpreadNecriumMod }
    }


    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            rawDescription = rawDescription.replace(s, "")
            initializeDescription()
        }
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription $s"
    }
}