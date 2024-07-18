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
        setMagicNumber(1)
        CardModifierManager.addModifier(this, ExhaustMod())
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(GainGoldAction(magicNumber))
        dealDamage(p, m, damage)
        drawCard(magicNumber)

    }

    override fun onTrade() {
        val draw = if (upgraded) 3 else 2
        drawCard(draw)
        upgradeDamage(if (upgraded) 3 else 5)
        upgradeMagicNumber(if (upgraded) 1 else 2)
        isDamageModified = true
        isMagicNumberModified = true
    }
}