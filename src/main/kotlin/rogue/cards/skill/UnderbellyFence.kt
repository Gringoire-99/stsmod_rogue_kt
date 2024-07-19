package rogue.cards.skill

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.drawCard
import utils.isOtherClassCard

class UnderbellyFence() :
    AbstractRogueCard(
        name = UnderbellyFence::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            return hand.size() > 1
        }

        return true
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(1, "选择一张卡消耗", false, false, { true }) { cards ->
            val t = cards[0]
            addToBot(GainEnergyAction(1))
            drawCard(1)
            if (t.isOtherClassCard(p?.cardColor)) {
                addToBot(GainEnergyAction(magicNumber))
                drawCard(magicNumber)
            }
        })
    }
}