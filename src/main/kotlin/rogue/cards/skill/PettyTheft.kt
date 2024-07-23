package rogue.cards.skill

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import utils.generateCardChoices

class PettyTheft :
    AbstractRogueCard(
        name = PettyTheft::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val filter = CardFilter(costFilter = { it == 0 })
        val generateCardChoices = generateCardChoices(filter, magicNumber)
        generateCardChoices.forEach {
            addToBot(MakeTempCardInHandAction(it))
        }

    }
}