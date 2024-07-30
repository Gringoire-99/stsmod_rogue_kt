package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.addMod

class TheCoin() :
    AbstractRogueCard(
        name = TheCoin::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    init {
        setMagicNumber(1)
        addMod(ExhaustMod(), RetainMod())
    }

    companion object {
        val preview = TheCoin()
    }

    override fun upgrade() {
        useUpgrade { upgradeMagicNumber(1) }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(GainEnergyAction(magicNumber))
    }
}