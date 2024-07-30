package rogue.cards.power

import basemod.cardmods.RetainMod
import basemod.helpers.BaseModCardTags
import common.CardFilter
import rogue.cards.AbstractMimicCard
import utils.addMod
import utils.generateCardChoices

class MaestraMaskMerchant() :
    AbstractMimicCard(
        name = MaestraMaskMerchant::class.simpleName.toString(),
        cost = 2,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(RetainMod())
        tags.add(BaseModCardTags.FORM)
    }

    override fun atTurnStartPreDraw() {
        mimicToRandomForm()
    }

    private fun mimicToRandomForm() {
        val take = generateCardChoices(CardFilter(includeTags = hashSetOf(BaseModCardTags.FORM)), 1).firstOrNull()
        take?.let {
            this.mimic(it)
            this.cost = 2
            this.costForTurn = 2
        }
    }

    override fun triggerWhenDrawn() {
        mimicToRandomForm()
    }


}