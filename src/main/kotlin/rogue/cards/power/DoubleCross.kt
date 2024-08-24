package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.power.secret.DoubleCrossPower
import utils.makeId

class DoubleCross :
    AbstractSecretCard(
        rawName = DoubleCross::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = DoubleCrossPower::class.makeId()
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
        addToBot(ApplyUniquePowerAction(DoubleCrossPower(p ?: AbstractDungeon.player, magicNumber)))

    }
}