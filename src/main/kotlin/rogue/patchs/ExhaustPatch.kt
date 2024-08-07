package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.action.TriggerExhaustCardAction

class ExhaustPatch {
    @SpirePatch2(clz = CardGroup::class, method = "moveToExhaustPile")
    internal class BeforeExhaust {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                AbstractDungeon.actionManager.addToTop(TriggerExhaustCardAction())
            }
        }
    }
}