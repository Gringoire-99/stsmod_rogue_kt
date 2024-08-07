package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.secret.EvasionPower
import utils.makeId

class Evasion :
    AbstractRogueCard(
        name = Evasion::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(7)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            if(hasPower(EvasionPower::class.makeId())){
                return false
            }
        }
        return true
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(4)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, EvasionPower(p ?: AbstractDungeon.player, magicNumber)))

    }
}