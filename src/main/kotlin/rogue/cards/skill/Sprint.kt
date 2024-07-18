package rogue.cards.skill

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.drawCard
import utils.gainBlock

class Sprint() :
    AbstractRogueCard(
        name = Sprint::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF,
    ) {
    init {
        setBlock(10)
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
            upgradeBlock(4)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, block)
        drawCard(magicNumber)
    }
}