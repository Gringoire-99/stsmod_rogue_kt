package rogue.cards.skill

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.generateCardChoices

class TwistedPack() :
    AbstractRogueCard(
        name = TwistedPack::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        addMod(ExhaustMod())
        setMagicNumber(5)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val cards = generateCardChoices(number = magicNumber)
        cards.forEach {
            if (upgraded) {
                it.upgrade()
            }
            it.addMod(EtherealMod())
            p?.apply {
                hand.addToHand(it)
            }
        }
    }
}