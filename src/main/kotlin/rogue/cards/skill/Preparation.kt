package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.cards.AbstractRogueCard
import rogue.mods.ReduceCostMod
import rogue.mods.TurnEffectMod
import utils.addMod

class Preparation :
    AbstractRogueCard(
        name = Preparation::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(2)
        addMod(ExhaustMod(), RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(magicNumber, TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0], { card ->
            card != this
        }, { cards ->
            cards.forEach { card ->
                card.retain = true
                card.addMod(TurnEffectMod(1) {
                    it.addMod(ReduceCostMod(1, isTurnEffect = true, removeOnPlay = true))
                })
            }

        }))
    }
}