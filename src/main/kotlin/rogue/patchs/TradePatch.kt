package rogue.patchs

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import common.TradeCard
import rogue.cards.Tradeable

class TradePatch {
    @SpirePatch2(clz = AbstractPlayer::class, method = "useCard")
    internal class BeforeUseCard {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(
                __instance: AbstractPlayer?,
                c: AbstractCard?,
                monster: AbstractMonster?,
                energyOnUse: Int
            ): SpireReturn<Void>? {
                if (c is Tradeable && c.isEnableTrade) {
                    val trade = TradeCard()
                    AbstractDungeon.actionManager.addToTop(
                        SelectCardsAction(
                            arrayListOf(c, trade),
                            TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0]
                        ) { cards ->
                            val selection = cards[0]
                            if (selection is TradeCard) {
                                if (EnergyPanel.getCurrentEnergy() >= 1) {
                                    c.onTrade()
                                    EnergyPanel.useEnergy(1)
                                    AbstractDungeon.actionManager.addToTop(DiscardSpecificCardAction(c))
                                }
                            } else {
                                c.isEnableTrade = false
                                __instance?.useCard(c, monster, energyOnUse)
                                c.isEnableTrade = true
                            }
                        })
                    return SpireReturn.Return()
                }
                return SpireReturn.Continue()
            }


        }
    }
}