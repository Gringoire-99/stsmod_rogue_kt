package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.common.GainGoldAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.Tradeable
import utils.dealDamage
import utils.drawCard

class SISevenExtortion(override var isEnableTrade: Boolean = true) :
    AbstractRogueCard(
        name = SISevenExtortion::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ), Tradeable {
    init {
        setDamage(6)
        setMagicNumber(2)
        CardModifierManager.addModifier(this, ExhaustMod())
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(GainGoldAction(magicNumber))
        dealDamage(p, m, damage)
        drawCard(magicNumber)
    }

    override fun onTrade() {
        drawCard(if (upgraded) 3 else 2)
        upgradeDamage(if (upgraded) 5 else 3)
        upgradeMagicNumber(if (upgraded) 3 else 2)
    }
}