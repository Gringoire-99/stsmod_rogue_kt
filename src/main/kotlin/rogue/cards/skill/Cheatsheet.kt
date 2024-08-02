package rogue.cards.skill

import basemod.cardmods.EtherealMod
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.action.EmptyAction
import rogue.cards.AbstractMimicCard
import utils.addMod
import utils.generateCardChoices

class Cheatsheet :
    AbstractMimicCard(
        name = Cheatsheet::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ) {

    init {
        addMod(EtherealMod())
    }

    companion object {
        val preview = Cheatsheet()
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        addToBot(EmptyAction {
            generateCardChoices(number = 1, upgraded = upgraded).firstOrNull()?.let {
                this.mimic(it)
            }
        })
    }

}