package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.secret.AmbushPower
import utils.makeId

class Ambush :
    AbstractRogueCard(
        name = Ambush::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {


    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            if (hasPower(AmbushPower::class.makeId())) {
                return false
            }
        }
        return true
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, AmbushPower(p ?: AbstractDungeon.player)))
    }
}