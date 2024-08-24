package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.power.secret.BamboozlePower
import utils.makeId
import utils.upMagicNumber

class Bamboozle() :
    AbstractSecretCard(
        rawName = Bamboozle::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = BamboozlePower::class.makeId()
    ) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyUniquePowerAction(BamboozlePower(p ?: AbstractDungeon.player, magicNumber)))
    }
}