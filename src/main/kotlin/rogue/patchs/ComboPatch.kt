package rogue.patchs

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import common.TradeCard
import rogue.cards.AbstractComboCard
import rogue.cards.Tradeable
import utils.logger
import utils.tradeStrings

class ComboPatch {
    @SpirePatch2(clz = AbstractPlayer::class, method = "useCard")
    internal class AfterUsedCard {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun postfix(c: AbstractCard?){
                logger.info("出牌后：是否连击牌：${c is AbstractComboCard} ")
                if (c is AbstractComboCard) {
                    logger.info("是否触发连击效果:${c.isEnableComboEffect}")
                }
                if (c is AbstractComboCard && c.isEnableComboEffect) {
                    logger.info("关闭连击")
                    c.isEnableComboEffect = false
                    AbstractComboCard.isComboOn = false
                } else {
                    logger.info("打开连击")
                    AbstractComboCard.isComboOn = true
                }

            }
        }
    }

}
