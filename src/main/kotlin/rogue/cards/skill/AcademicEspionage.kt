package rogue.cards.skill

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.mods.DrawCardMod
import rogue.mods.ReduceCostMod
import utils.addMod
import utils.generateCardChoices

class AcademicEspionage :
    AbstractRogueCard(
        name = AcademicEspionage::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(10)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val cards = generateCardChoices(number = magicNumber)
        cards.forEach {
            it.addMod(ExhaustMod(), EtherealMod(), ReduceCostMod(1), DrawCardMod())
            addToBot(MakeTempCardInDrawPileAction(it, 1, true, true))

        }
    }
}