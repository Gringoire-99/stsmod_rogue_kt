package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.AddCardInDiscardPile
import rogue.cards.AbstractRogueCard

class GangUp() :
    AbstractRogueCard(
        name = GangUp::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade { upgradeBaseCost(0) }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(1, "选择一张卡消耗", false, false, { true }) { cards ->
            cards[0].apply {
                repeat(3) {
                    addToTop(AddCardInDiscardPile(makeStatEquivalentCopy()))
                }
                addToBot(ExhaustSpecificCardAction(this@apply, p?.hand))
            }
        })
    }
}