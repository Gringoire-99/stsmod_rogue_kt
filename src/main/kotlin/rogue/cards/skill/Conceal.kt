package rogue.cards.skill

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.common.StealthPower
import utils.gainBlock
import utils.makeId

class Conceal :
    AbstractRogueCard(
        name = Conceal::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setBlock(4)
        setMagicNumber(10)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBlock(3)
            upgradeMagicNumber(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val b = if (p?.hasPower(StealthPower::class.makeId()) == true) block + magicNumber else block
        this.addToBot(ApplyPowerAction(p, p, StealthPower(p ?: AbstractDungeon.player)))
        gainBlock(p, b)
    }
}