package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.IntangiblePower
import rogue.cards.AbstractRogueCard
import rogue.power.StealthPower
import utils.gainBlock
import utils.makeId

class CloakOfShadows() :
    AbstractRogueCard(
        name = CloakOfShadows::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        CardModifierManager.addModifier(this, RetainMod())
        CardModifierManager.addModifier(this, ExhaustMod())
        setBlock(10)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBlock(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            if (p.hasPower(StealthPower::class.makeId())) {
                addToBot(ApplyPowerAction(p, p, IntangiblePower(p, 1), 1))
            } else {
                gainBlock(p, block)
                addToBot(ApplyPowerAction(p, p, StealthPower(p), 1))
            }
        }
    }
}