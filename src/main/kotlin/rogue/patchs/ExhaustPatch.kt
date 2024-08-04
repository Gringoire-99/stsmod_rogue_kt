package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.action.EmptyAction
import rogue.cards.OnExhaustInterface
import utils.logger

class ExhaustPatch {
    @SpirePatch2(clz = CardGroup::class, method = "moveToExhaustPile")
    internal class BeforeExhaust {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix() {
                logger.info("======= card exhausted ${AbstractDungeon.player}=======")
                AbstractDungeon.actionManager.addToTop(EmptyAction {
                    AbstractDungeon.player.hand.group.forEach {
                        if (it is OnExhaustInterface) {
                            it.afterCardExhausted()
                        }
                    }
                })
            }
        }
    }
}