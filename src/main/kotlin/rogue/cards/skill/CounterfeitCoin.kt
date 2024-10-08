package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.drawCard

class CounterfeitCoin :
    AbstractRogueCard(
        name = CounterfeitCoin::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
        addMod(ExhaustMod(), RetainMod())

    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(GainEnergyAction(magicNumber))
        if (upgraded) {
            drawCard(magicNumber)
        }
    }
}