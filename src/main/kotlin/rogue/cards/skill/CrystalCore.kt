package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import rogue.cards.AbstractRogueCard
import utils.addMod

class CrystalCore() :
    AbstractRogueCard(
        name = CrystalCore::class.simpleName.toString(),
        cost = 3,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    init {
        setMagicNumber(5)
        addMod(ExhaustMod(), RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, StrengthPower(p, magicNumber), magicNumber))
        addToBot(ApplyPowerAction(p, p, DexterityPower(p, magicNumber), magicNumber))
    }
}