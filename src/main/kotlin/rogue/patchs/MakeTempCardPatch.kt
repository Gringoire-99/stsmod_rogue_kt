package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.cards.OnMakeTempCard
import utils.logger


class MakeTempCardPatch {
    @SpirePatch2(
        clz = MakeTempCardInHandAction::class,
        method = "<ctor>",
        paramtypez = [AbstractCard::class, Int::class]
    )
    internal class MakeTempCardInHand {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(card: AbstractCard?) {
                card?.call()
            }
        }
    }

    @SpirePatch2(
        clz = MakeTempCardInHandAction::class,
        method = "<ctor>",
        paramtypez = [AbstractCard::class, Boolean::class]
    )
    internal class MakeTempCardInHand5 {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(card: AbstractCard?) {
                card?.call()
            }
        }
    }

    @SpirePatch2(
        clz = MakeTempCardInDrawPileAction::class,
        method = "<ctor>",
        paramtypez = [AbstractCard::class, Int::class, Boolean::class, Boolean::class, Boolean::class, Float::class, Float::class]
    )
    internal class MakeTempCardInHand2 {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(card: AbstractCard?) {
                card?.call()
            }
        }
    }

    @SpirePatch2(
        clz = MakeTempCardInDiscardAction::class,
        method = "<ctor>",
        paramtypez = [AbstractCard::class, Int::class]
    )
    internal class MakeTempCardInHand3 {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(card: AbstractCard?) {
                card?.call()
            }
        }
    }

    @SpirePatch2(
        clz = MakeTempCardInDiscardAndDeckAction::class,
        method = "<ctor>",
        paramtypez = [AbstractCard::class]
    )
    internal class MakeTempCardInHand4 {
        companion object {
            @JvmStatic
            @SpirePrefixPatch
            fun prefix(card: AbstractCard?) {
                card?.call()

            }
        }
    }
}


fun AbstractCard.call() {
    logger.info("=======making temp card=======")
    AbstractDungeon.player?.apply {
        powers.forEach {
            if (it is OnMakeTempCard) {
                it.onMakeTempCard(this@call)
            }

        }
    }
}