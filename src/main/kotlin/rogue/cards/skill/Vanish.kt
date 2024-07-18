package rogue.cards.skill

import com.megacrit.cardcrawl.actions.common.DiscardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.gainBlock

class Vanish() :
    AbstractRogueCard(
        name = Vanish::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setBlock(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBlock(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val amount = p?.hand?.group?.size ?: 0
        addToBot(DiscardAction(p, p, amount, false))
        repeat(amount - 1) {
            gainBlock(p, block)
        }
    }
}