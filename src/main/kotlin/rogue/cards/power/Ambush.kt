package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.power.secret.AmbushPower
import utils.makeId

class Ambush :
    AbstractSecretCard(
        rawName = Ambush::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.COMMON,
        powerId = AmbushPower::class.makeId()
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
        addToBot(ApplyUniquePowerAction(AmbushPower(p ?: AbstractDungeon.player, magicNumber)))
    }
}