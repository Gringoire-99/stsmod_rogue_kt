package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.addToQueue

class ContrabandStash() :
    AbstractRogueCard(
        name = ContrabandStash::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
        GraveField.grave.set(this, true)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            var count = magicNumber
            val groupingBy = cardUsedCombatOC.groupBy { it.rarity }
            val rarity = arrayListOf(
                CardRarity.RARE,
                CardRarity.UNCOMMON,
                CardRarity.COMMON,
                CardRarity.BASIC,
                CardRarity.SPECIAL
            )
            rarity.forEach {
                groupingBy[it]?.shuffled()?.forEach {
                    if (count != 0) {
                        count--
                        addToQueue(it.makeStatEquivalentCopy(), null, random = true, purge = true)
                    }
                }
            }
        })
    }
}