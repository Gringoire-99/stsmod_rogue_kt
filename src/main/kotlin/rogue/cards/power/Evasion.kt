package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractSecretCard
import rogue.power.secret.EvasionPower

class Evasion :
    AbstractSecretCard(
        rawName = Evasion::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
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
        addToBot(ApplyPowerAction(p, p, EvasionPower(p ?: AbstractDungeon.player, magicNumber)))

    }
}