package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.secret.DoubleCrossPower
import utils.makeId

class DoubleCross :
    AbstractRogueCard(
        name = DoubleCross::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            if (hasPower(DoubleCrossPower::class.makeId())) {
                return false
            }
        }
        return true
    }

    override fun upgrade() {
        upgradeMagicNumber(1)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, DoubleCrossPower(p ?: AbstractDungeon.player, magicNumber)))

    }
}