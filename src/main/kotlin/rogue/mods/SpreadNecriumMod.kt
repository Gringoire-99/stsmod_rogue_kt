package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.action.SpreadNecriumAction

class SpreadNecriumMod : AbstractCardModifier() {


    override fun onExhausted(card: AbstractCard?) {
        addToBot(SpreadNecriumAction())
    }

    override fun makeCopy(): AbstractCardModifier {
        return SpreadNecriumMod()
    }


    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription NL *死金传播"
    }
}