package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractMimicCard
import utils.addMod
import utils.upBase

class ShadowOfDemise() :
    AbstractMimicCard(
        name = ShadowOfDemise::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.NONE
    ) {


    init {
        setMagicNumber(1)
        addMod(RetainMod(), ExhaustMod())
    }

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(1)
    }

    override fun triggerOnOtherCardPlayed(otherCard: AbstractCard?) {
        otherCard?.let {
            if (it.type == CardType.SKILL && it !is ShadowOfDemise) {
                val copy: AbstractCard = it.makeStatEquivalentCopy()
                copy.upBase(magicNumber)
                this.mimic(copy)
                if (timesUpgraded > 0) {
                    updateName()
                }
            }
        }
    }

    override fun upgradeName() {
        timesUpgraded++
        upgraded = true
        updateName()
    }

    private fun updateName() {
        if (mimicTarget != null) {
            this.name = "${mimicTarget?.name}+${timesUpgraded}"
        } else {
            this.name = "${name}+${timesUpgraded}"
        }
        initializeTitle()
    }


}