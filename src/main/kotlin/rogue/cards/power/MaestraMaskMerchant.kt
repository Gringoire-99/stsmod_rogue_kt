package rogue.cards.power

import basemod.cardmods.RetainMod
import basemod.helpers.BaseModCardTags
import common.CardFilter
import rogue.cards.AbstractMimicCard
import utils.addMod
import utils.generateCardChoices
import kotlin.math.min

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

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(1)
        }
    }

    override fun atTurnStartPreDraw() {
        mimicToRandomForm()
    }

    private fun mimicToRandomForm() {
        val take = generateCardChoices(CardFilter(includeTags = hashSetOf(BaseModCardTags.FORM)), 1).firstOrNull()
        take?.let {
            it.cost = min(it.cost, this.cost)
            it.costForTurn = min(it.costForTurn, this.costForTurn)
            if (upgraded) it.upgrade()
            this.mimic(it)
            this.isEthereal = false
            retain = true
            selfRetain = true
        }
    }

    override fun triggerWhenDrawn() {
        mimicToRandomForm()
    }


}