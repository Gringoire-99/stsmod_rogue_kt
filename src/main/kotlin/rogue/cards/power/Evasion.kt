package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.power.secret.EvasionPower
import utils.makeId

class Evasion :
    AbstractSecretCard(
        rawName = Evasion::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = EvasionPower::class.makeId()
    ) {
    init {
        setMagicNumber(7)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(4)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyUniquePowerAction(EvasionPower(p ?: AbstractDungeon.player, magicNumber)))

    }
}