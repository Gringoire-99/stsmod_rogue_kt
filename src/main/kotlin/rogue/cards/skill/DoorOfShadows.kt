package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.OnExhaustInterface
import rogue.cards.impls.LevelInterfaceImpl
import rogue.mods.ReduceCostMod
import utils.addMod
import utils.modId
import kotlin.math.max

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
            this.name = "暗影之门"
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
                addToTop(SelectCardsAction(p.exhaustPile.group, magicNumber, "选择${magicNumber}张") { cards ->
                    cards.forEach {
                        addToBot(MakeTempCardInHandAction(it.apply {
                            if (this@DoorOfShadows.isMaxLevel()) {
                                this@apply.addMod(ReduceCostMod(max(this@apply.cost, this@apply.costForTurn)))
                            }
                        }))
                    }
                })
            }
        })
    }

    override fun afterCardExhausted() {
        if (!isMaxLevel()) {
            exp++
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as DoorOfShadows
        copy.level = level
        copy.exp = exp
        return copy
    }
}