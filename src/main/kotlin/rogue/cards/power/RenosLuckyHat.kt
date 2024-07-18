package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.makeId

class RenosLuckyHat() :
    AbstractRogueCard(
        name = RenosLuckyHat::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON,
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

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return !AbstractDungeon.player.hasPower(rogue.power.RenosLuckyHat::class.makeId())
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                rogue.power.RenosLuckyHat(p ?: AbstractDungeon.player, this.makeStatEquivalentCopy() as RenosLuckyHat),
                1
            )
        )
    }
}