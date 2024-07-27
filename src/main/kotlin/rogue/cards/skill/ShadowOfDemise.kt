package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.Mimicable
import utils.addMod
import utils.mimic
import utils.upBase

class ShadowOfDemise() :
    AbstractRogueCard(
        name = ShadowOfDemise::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.NONE
    ), Mimicable {
    var realMagic: Int
    var initialName = name
    override var targetCard: AbstractCard? = null

    init {
        setMagicNumber(1)
        realMagic = this.magicNumber
        addMod(RetainMod(), ExhaustMod())
    }

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(1)
        realMagic = this.magicNumber
    }

    override fun triggerOnOtherCardPlayed(otherCard: AbstractCard?) {
        otherCard?.apply {
            if (otherCard.type == CardType.SKILL && otherCard != this@ShadowOfDemise) {
                val copy: AbstractCard = otherCard.makeStatEquivalentCopy()
                copy.upBase(realMagic)
                this@ShadowOfDemise.mimic(copy)
                if (timesUpgraded > 0) {
                    updateName()
                }
                this.exhaust = true
            }
        }
    }

    override fun upgradeName() {
        timesUpgraded++
        upgraded = true
        updateName()
    }

    fun updateName() {
        if (targetCard != null) {
            this.name = "${targetCard?.name}+${timesUpgraded}"
        } else {
            this.name = "${initialName}+${timesUpgraded}"
        }
        initializeTitle()
    }


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