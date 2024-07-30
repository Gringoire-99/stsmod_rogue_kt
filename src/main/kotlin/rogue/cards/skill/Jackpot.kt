package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.generateCardChoices

class Jackpot :
    AbstractRogueCard(
        name = Jackpot::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val filter = CardFilter(cost = { it >= 2 })
        val generateCardChoices = generateCardChoices(filter, magicNumber, upgraded = upgraded)
        generateCardChoices.forEach {
            it.addMod(RetainMod())
            addToBot(MakeTempCardInHandAction(it))
            it.addMod(ExhaustMod())
        }

    }
}