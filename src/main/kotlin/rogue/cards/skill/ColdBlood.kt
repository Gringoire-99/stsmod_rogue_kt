package rogue.cards.skill

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.LoseStrengthPower
import com.megacrit.cardcrawl.powers.StrengthPower
import rogue.cards.AbstractComboCard

class ColdBlood :
    AbstractComboCard(
        name = ColdBlood::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        var amount = magicNumber
        useCombo {
            amount += 2
        }
        addToBot(ApplyPowerAction(p, p, StrengthPower(p, amount), amount))
        addToBot(ApplyPowerAction(p, p, LoseStrengthPower(p, amount), amount))
    }
}