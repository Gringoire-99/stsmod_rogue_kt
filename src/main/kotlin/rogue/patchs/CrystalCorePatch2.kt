package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.action.EmptyAction
import rogue.cards.OnAddCard
import rogue.power.common.CrystalCorePower
import utils.makeId


class CrystalCorePatch2 {
    @SpirePatch2(
        clz = CardGroup::class,
        method = "addToTop",
    )
    internal class AddToTop {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(c: AbstractCard?) {
                c?.apply { trigger(this) }
            }
        }
    }

    @SpirePatch2(
        clz = CardGroup::class,
        method = "addToHand",
    )
    internal class AddToHand {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(c: AbstractCard?) {
                c?.apply { trigger(this) }
            }
        }
    }

    @SpirePatch2(
        clz = CardGroup::class,
        method = "addToBottom",
    )
    internal class AddToBottom {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(c: AbstractCard?) {
                c?.apply { trigger(this) }
            }
        }
    }

    @SpirePatch2(
        clz = CardGroup::class,
        method = "addToRandomSpot",
    )
    internal class AddToRandomSpot {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(c: AbstractCard?) {
                c?.apply { trigger(this) }
            }
        }
    }

    @SpirePatch2(
        clz = AbstractCard::class,
        method = "makeSameInstanceOf",
    )
    internal class makeSameInstanceOf {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(__instance: AbstractCard?, __result: AbstractCard?) {
                __instance?.apply {
                    __result.let {
                        CrystalCorePatch.Fields.isCrystalCoreEnhanced.set(
                            it,
                            CrystalCorePatch.Fields.isCrystalCoreEnhanced.get(this)
                        )
                    }
                }
            }
        }
    }

    @SpirePatch2(
        clz = AbstractCard::class,
        method = "makeStatEquivalentCopy",
    )
    internal class makeStatEquivalentCopy {
        companion object {
            @JvmStatic
            @SpirePostfixPatch
            fun post(__instance: AbstractCard?, __result: AbstractCard?) {
                __instance?.apply {
                    __result.let {
                        CrystalCorePatch.Fields.isCrystalCoreEnhanced.set(
                            it,
                            CrystalCorePatch.Fields.isCrystalCoreEnhanced.get(this)
                        )
                    }
                }
            }
        }
    }
}


fun trigger(c: AbstractCard) {
    if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.player.hasPower(
            CrystalCorePower::class.makeId()
        )
    ) {
        AbstractDungeon.actionManager.addToBottom(EmptyAction {
            AbstractDungeon.player?.apply {
                powers.forEach {
                    if (it is OnAddCard) {
                        it.onAddCard(c)
                    }
                }
            }
        })
    }

}