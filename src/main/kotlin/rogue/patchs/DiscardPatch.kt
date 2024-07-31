package rogue.patchs

import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.mods.OnDiscard

class DiscardPatch {
    @SpirePatch2(clz = DiscardSpecificCardAction::class, method = "update")
    internal class BeforeDiscard {
        companion object {
            @JvmStatic
            @SpireInsertPatch(rloc = 7)
            fun insert(___targetCard: AbstractCard?) {
                ___targetCard?.apply {
                    val modifiers = CardModifierManager.modifiers(this)
                    modifiers.forEach {
                        if (it is OnDiscard) {
                            it.onManualDiscard(this)
                        }
                    }
                }
            }
        }
    }
}