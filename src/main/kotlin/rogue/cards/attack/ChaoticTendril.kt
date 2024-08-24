package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ChaoticTendrilAction
import rogue.cards.AbstractRogueCard
import rogue.cards.CavernCard
import rogue.characters.Rogue
import utils.addMod
import utils.upMagicNumber

class ChaoticTendril :
    AbstractRogueCard(
        name = ChaoticTendril::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.ENEMY,
        color = CardColor.COLORLESS
    ), CavernCard {
    init {
        setMagicNumber(1)
        addMod(ExhaustMod())
    }

    companion object {
        val preview = ChaoticTendril()
    }

    override fun upgrade() {
        useUpgrade { upMagicNumber(1) }
    }

    override fun triggerOnEndOfPlayerTurn() {
        freeToPlayOnce = true
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val count = Rogue.chaoticTendrilCount
        m?.let {
            addToTop(ChaoticTendrilAction(count, it))
        }
        Rogue.chaoticTendrilCount += magicNumber
    }
}