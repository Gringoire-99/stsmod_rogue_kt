package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractSecretCard
import rogue.power.secret.PerjuryPower
import utils.makeId

class Perjury() :
    AbstractSecretCard(
        rawName = Perjury::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = PerjuryPower::class.makeId()
    ) {
    init {
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, PerjuryPower(p ?: AbstractDungeon.player, magic = magicNumber)))
    }
}