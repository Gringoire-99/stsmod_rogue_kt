package rogue.cards.skill

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.cards.AbstractRogueCard
import rogue.cards.attack.Backstab
import rogue.cards.attack.FanOfKnives
import utils.addMod

class ValeerasGift :
    AbstractRogueCard(
        name = ValeerasGift::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        addMod(RetainMod(), ExhaustMod())
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        repeat(magicNumber) {
            addToBot(
                SelectCardsAction(
                    arrayListOf(Backstab(), DeadlyPoison(), FanOfKnives()),
                    1,
                    TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0],
                    false,
                    { true },
                ) { cards ->
                    cards.forEach {
                        val target = it.makeStatEquivalentCopy()
                        if (upgraded) target.upgrade()
                        target.addMod(EtherealMod())
                        p?.apply {
                            addToBot(MakeTempCardInHandAction(target))
                        }
                    }
                }
            )
        }
    }
}