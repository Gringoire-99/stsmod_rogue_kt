package rogue.mods

import basemod.abstracts.AbstractCardModifier
import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.cards.AbstractCard
import utils.addMod
import utils.makeId
import utils.removeMod

class TempCardMod : AbstractCardModifier() {
    companion object {
        val id = TempCardMod::class.makeId()
    }

    override fun shouldApply(card: AbstractCard?): Boolean {
        return CardModifierManager.hasModifier(card, id)
    }

    override fun onInitialApplication(card: AbstractCard?) {
        card?.apply {
            removeMod(true, RetainMod.ID)
            addMod(ExhaustMod(), EtherealMod())
            exhaust = true
            isEthereal = true
            retain = false
            selfRetain = false
        }
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }

    override fun makeCopy(): AbstractCardModifier {
        return TempCardMod()
    }
}