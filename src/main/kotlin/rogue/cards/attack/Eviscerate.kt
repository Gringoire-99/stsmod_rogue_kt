package rogue.cards.attack

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.VulnerablePower
import rogue.cards.AbstractComboCard
import utils.dealDamage

class Eviscerate() :
    AbstractComboCard(
        name = Eviscerate::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(6)
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        if (!useCombo {
                dealDamage(p, m, damage + magicNumber)
                addToBot(ApplyPowerAction(m, p, VulnerablePower(m, 2, false), 2))
            }) {
            dealDamage(p, m, damage)
        }
    }
}