package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import rogue.action.PlayTwiceAction
import utils.addMod
import utils.makeId

class NecriumMod() : AbstractCardModifier() {
    override fun makeCopy(): AbstractCardModifier {
        return NecriumMod()
    }

    companion object {
        val id = NecriumMod::class.makeId()
    }

    private val cardString: CardStrings = CardCrawlGame.languagePack.getCardStrings(id)

    private val s: String = cardString.DESCRIPTION
    override fun onUse(card: AbstractCard?, target: AbstractCreature?, action: UseCardAction?) {
        val copy = card?.makeStatEquivalentCopy()
        CardModifierManager.modifiers(copy).removeIf {
            it is NecriumMod || it is SpreadNecriumMod
        }
        copy?.apply {
            purgeOnUse = false
            exhaust = false
            addMod(ExhaustMod())
            addToTop(PlayTwiceAction(this, target))
        }
    }


    override fun onInitialApplication(card: AbstractCard?) {
        card?.exhaust = true
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            rawDescription = rawDescription.replace(s, "")
            initializeDescription()
        }
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return true
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription $s"
    }
}