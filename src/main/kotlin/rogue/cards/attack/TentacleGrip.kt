package rogue.cards.attack

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractComboCard
import utils.dealDamage

class TentacleGrip() :
    AbstractComboCard(
        name = TentacleGrip::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(3)
        setMagicNumber(1)
        cardsToPreview = ChaoticTendril.preview
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
        useCombo {
            addToBot(MakeTempCardInHandAction(ChaoticTendril(), magicNumber))
        }
    }
}