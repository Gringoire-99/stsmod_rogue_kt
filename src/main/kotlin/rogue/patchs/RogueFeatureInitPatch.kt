package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.characters.Rogue

class RogueFeatureInitPatch {
    @SpirePatch2(clz = AbstractPlayer::class, method = "applyStartOfCombatLogic")
    internal class StartOfCombat {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                Rogue.startOfCombatLogic()
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer::class, method = "applyStartOfTurnCards")
    internal class StartOfTurn {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                Rogue.startOfTurnLogic()
            }
        }
    }
}