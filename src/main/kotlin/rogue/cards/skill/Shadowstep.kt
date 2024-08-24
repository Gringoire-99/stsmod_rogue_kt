package rogue.cards.skill

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.action.EmptyAction
import rogue.cards.AbstractComboCard
import rogue.mods.ReduceCostMod
import utils.addMod

class Shadowstep :
    AbstractComboCard(
        name = Shadowstep::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        ExhaustiveVariable.setBaseValue(this, 2)
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            ExhaustiveVariable.upgrade(this, 1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val magic = magicNumber
        useCombo {
            addToBot(SelectCardsAction(
                AbstractDungeon.player.discardPile.group,
                magic,
                TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0],
                true,
                { true }) { cards ->
                cards.forEach {
                    if (upgraded) {
                        addToTop(EmptyAction {
                            it.addMod(
                                ReduceCostMod(
                                    amount = 1,
                                    removeOnPlay = true,
                                    removeOnEndOfTurn = true,
                                    isTurnEffect = true
                                )
                            )
                        })
                    }
                    addToTop(
                        MoveCardsAction(
                            AbstractDungeon.player.hand,
                            AbstractDungeon.player.discardPile
                        ) { c: AbstractCard -> c == it })
                }
            })
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return isComboOn && p?.discardPile?.isEmpty == false
    }
}