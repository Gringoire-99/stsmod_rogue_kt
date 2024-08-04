package rogue.cards.power

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import rogue.action.CrystalCoreAction
import rogue.cards.AbstractRogueCard
import rogue.power.common.CrystalCorePower
import utils.addMod

class CrystalCore :
    AbstractRogueCard(
        name = CrystalCore::class.simpleName.toString(),
        cost = 2,
        type = CardType.POWER,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    init {
        setMagicNumber(3)
        addMod(RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    companion object {
        val preview = CrystalCore()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            addToBot(ApplyPowerAction(p, p, StrengthPower(p, magicNumber), magicNumber))
            addToBot(ApplyPowerAction(p, p, DexterityPower(p, magicNumber), magicNumber))
            addToBot(CrystalCoreAction(magicNumber))
            addToBot(ApplyPowerAction(p, p, CrystalCorePower(p, magicNumber), magicNumber))
        }
    }

}