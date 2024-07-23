package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.AbstractComboCard

class AfterCardPlayedPatch {
    @SpirePatch2(clz = AbstractPlayer::class, method = "useCard")
    internal class AfterUsedCard {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix(c: AbstractCard?) {
                if (c is AbstractComboCard && c.isEnableComboEffect) {
                    c.isEnableComboEffect = false
                    AbstractComboCard.isComboOn = false
                } else {
                    AbstractComboCard.isComboOn = true
                }
//
//                c?.apply {
//                    AbstractDungeon.player?.apply {
//                        hand.group.forEach {
//                            if (it is OnAfterCardPlayed) {
//                                it.onAfterCardPlayed(c)
//                            }
//                        }
//                        drawPile.group.forEach {
//                            if (it is OnAfterCardPlayed) {
//                                it.onAfterCardPlayed(c)
//                            }
//                        }
//                        discardPile.group.forEach {
//                            if (it is OnAfterCardPlayed) {
//                                it.onAfterCardPlayed(c)
//                            }
//                        }
//                        limbo.group.forEach {
//                            if (it is OnAfterCardPlayed) {
//                                it.onAfterCardPlayed(c)
//                            }
//                        }
//                    }
//                }
            }
        }
    }

}
