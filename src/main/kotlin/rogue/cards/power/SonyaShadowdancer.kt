package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.common.ShadowdancePower

class SonyaShadowdancer() :
    AbstractRogueCard(
        name = SonyaShadowdancer::class.simpleName.toString(),
        cost = 3,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {


    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            addToBot(ApplyPowerAction(p, p, ShadowdancePower(p), 1))
        }
    }
}