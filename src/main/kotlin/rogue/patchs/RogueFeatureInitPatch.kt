package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.AbstractComboCard
import rogue.power.weapon.AbstractWeaponPower
import utils.logger

class RogueFeatureInitPatch {
    @SpirePatch2(clz = AbstractPlayer::class, method = "applyStartOfCombatLogic")
    internal class StartOfCombat {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                logger.info("========= start of combat ===========")
                AbstractComboCard.reset()
                AbstractWeaponPower.reset()
                AbstractWeaponPower.combatAttackCount = 0
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer::class, method = "applyStartOfTurnCards")
    internal class StartOfTurn {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                logger.info("========= start of turn ===========")
                AbstractComboCard.reset()
                AbstractWeaponPower.reset()
            }
        }
    }
}