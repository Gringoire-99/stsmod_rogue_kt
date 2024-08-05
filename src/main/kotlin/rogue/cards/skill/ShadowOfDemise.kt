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
    val initializerName: String

    init {
        setMagicNumber(1)
        addMod(RetainMod(), ExhaustMod())
        initializerName = name
    }

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(1)
    }

    override fun canUpgrade(): Boolean {
        return true
    }

    override fun triggerOnOtherCardPlayed(otherCard: AbstractCard?) {
        otherCard?.let {
            if ((it.type == CardType.SKILL || it.type == CardType.ATTACK)) {
                val copy: AbstractCard = it.makeStatEquivalentCopy()
                copy.upBase(magicNumber)
                this.mimic(copy)
                exhaust = true
                selfRetain = true
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
        this.name = "${initializerName}+${timesUpgraded}"
        initializeTitle()
    }


}