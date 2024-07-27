package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractComboCard
import utils.dealDamage
import utils.drawCard

class GhostlyStrike() :
    AbstractComboCard(
        name = GhostlyStrike::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY

    ) {
    init {
        tags.add(CardTags.STRIKE)
        setDamage(4)
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
        useCombo {
            drawCard(magicNumber)
        }
    }
}