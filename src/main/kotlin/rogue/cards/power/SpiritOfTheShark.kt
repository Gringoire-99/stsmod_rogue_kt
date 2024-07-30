package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.SpiritOfTheSharkPower

class SpiritOfTheShark() :
    AbstractRogueCard(
        name = SpiritOfTheShark::class.simpleName.toString(),
        cost = 2,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(1)
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return super.canUse(p, m)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(ApplyPowerAction(p, p, SpiritOfTheSharkPower(p ?: AbstractDungeon.player)))
    }
}