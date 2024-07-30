package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractMimicCard
import utils.addMod

class ShadowReflection :
    AbstractMimicCard(
        name = ShadowReflection::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ) {

    init {
        addMod(RetainMod(), ExhaustMod())
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.let {
            this.mimic(it)
        }
    }

}