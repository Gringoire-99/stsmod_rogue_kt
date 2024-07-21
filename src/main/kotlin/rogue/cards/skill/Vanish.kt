package rogue.cards.skill

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.gainBlock

class Vanish :
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
        addToBot(SelectCardsInHandAction(amount, "选择手牌丢弃", true, true, { true }) { cards ->
            cards.forEach {
                addToBot(DiscardSpecificCardAction(it))
                gainBlock(p, block)
            }
        })
    }
}