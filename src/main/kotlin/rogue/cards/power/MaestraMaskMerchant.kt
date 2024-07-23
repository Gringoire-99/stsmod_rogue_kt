package rogue.cards.power

import basemod.cardmods.RetainMod
import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import rogue.cards.Mimicable
import utils.addMod
import utils.generateCardChoices
import utils.mimic

class MaestraMaskMerchant() :
    AbstractRogueCard(
        name = MaestraMaskMerchant::class.simpleName.toString(),
        cost = -2,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ), Mimicable {
    init {
        addMod(RetainMod())
        tags.add(BaseModCardTags.FORM)
    }

    override fun atTurnStartPreDraw() {
        val take = generateCardChoices(CardFilter(includeTags = hashSetOf(BaseModCardTags.FORM)), 1).firstOrNull()
        take?.let {
            this.mimic(it)
            this.isEthereal = false
        }
    }

    override fun triggerWhenDrawn() {
        val take = generateCardChoices(CardFilter(includeTags = hashSetOf(BaseModCardTags.FORM)), 1).firstOrNull()
        take?.let {
            this.mimic(it)
            this.isEthereal = false
        }
    }

    override var targetCard: AbstractCard? = null
    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return targetCard?.canUse(p, m) ?: false
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        targetCard?.use(p, m)
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy()
        (copy as Mimicable).targetCard = targetCard
        return copy
    }
}