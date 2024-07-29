package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.AbstractComboCard
import rogue.cards.AbstractRogueCard
import utils.isOtherClassCard

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
                c?.apply {
                    if (this.isOtherClassCard() && !this.purgeOnUse) {
                        AbstractRogueCard.cardUsedCombatOC.add(this)
                    }
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
