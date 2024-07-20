package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import rogue.action.PlayTwiceAction
import utils.makeId

class NecriumMod : AbstractCardModifier() {
    override fun makeCopy(): AbstractCardModifier {
        return NecriumMod()
    }

    companion object {
        val id = NecriumMod::class.makeId()
    }

    override fun onUse(card: AbstractCard?, target: AbstractCreature?, action: UseCardAction?) {
        val copy = card?.makeStatEquivalentCopy()
        CardModifierManager.removeModifiersById(copy, id, false)
        copy?.apply {
            purgeOnUse = true
            exhaust = true
            addToTop(PlayTwiceAction(this, target))
        }

    }

    override fun onInitialApplication(card: AbstractCard?) {
        card?.exhaust = true
    }

    override fun onRemove(card: AbstractCard?) {
        card?.exhaust = false
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return true
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription NL *死金"
    }
}