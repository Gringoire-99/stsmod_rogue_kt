package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractSecretCard
import rogue.power.secret.DirtyTricksPower
import utils.makeId

class DirtyTricks :
    AbstractSecretCard(
        rawName = DirtyTricks::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.COMMON,
        powerId = DirtyTricksPower::class.makeId()
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
        addToBot(ApplyPowerAction(p, p, DirtyTricksPower(p ?: AbstractDungeon.player, magicNumber)))
    }
}