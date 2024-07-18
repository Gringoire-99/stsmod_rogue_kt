package rogue.cards.skill

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.gainBlock

class Defend() :
    AbstractRogueCard(
        name = Defend::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.BASIC,
        target = CardTarget.SELF,
    ) {
    init {
        setBlock(5)
        this.tags.apply {
            add(CardTags.STARTER_DEFEND)
        }
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBlock(4)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, block)
    }
}