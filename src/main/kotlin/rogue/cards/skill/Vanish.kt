package rogue.cards.skill

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.cards.AbstractRogueCard
import utils.gainBlock
import kotlin.math.min

class Vanish :
    AbstractRogueCard(
        name = Vanish::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setBlock(6)
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBlock(2)
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, block)
        val amount = min(p?.hand?.size() ?: 0, 3)
        addToBot(
            SelectCardsInHandAction(
                amount,
                TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0],
                true,
                true,
                { true }) { cards ->
                cards.forEach {
                    addToBot(DiscardSpecificCardAction(it))
                    gainBlock(p, magicNumber)
                }
            })
    }
}