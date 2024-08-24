package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.OnExhaustInterface
import rogue.cards.impls.LevelInterfaceImpl
import utils.addMod
import utils.modId

class DoorOfShadows(
    impl: LevelInterface = LevelInterfaceImpl(
        maxLevel1 = 2,
        maxExpr = 4,
    )
) :
    AbstractRogueCard(
        name = DoorOfShadows::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ), OnExhaustInterface, LevelInterface by impl {
    init {
        addMod(RetainMod(), ExhaustMod())
        setMagicNumber(1)
        impl.onMaxLevelCb = {
            this.loadCardImage("$modId/cards/skill/DoorOfShadowsInfused.png")
            initializeTitle()
        }
        cardsToPreview = preview
    }

    companion object {
        val preview = DoorOfShadows().apply { level = 2 }
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            p?.let {
                addToTop(
                    SelectCardsAction(
                        p.exhaustPile.group,
                        magicNumber,
                        TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0]
                    ) { cards ->
                        cards.forEach {
                            addToBot(
                                MoveCardsAction(
                                    AbstractDungeon.player.hand,
                                    AbstractDungeon.player.exhaustPile,
                                    { c -> c == it },
                                    1,
                                ) { cards ->
                                    cards.forEach {
                                        it.apply {
                                            if (this@DoorOfShadows.isMaxLevel()) {
                                                it.freeToPlayOnce = true
                                            }
                                        }
                                    }
                                })
                        }
                    })
            }
        })
    }

    override fun afterCardExhausted() {
        if (!isMaxLevel()) {
            exp++
            cardsToPreview = null
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as DoorOfShadows
        copy.level = level
        copy.exp = exp
        return copy
    }
}