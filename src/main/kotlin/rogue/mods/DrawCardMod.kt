package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import utils.drawCard
import utils.makeId

class DrawCardMod(val amount: Int = 1) : AbstractCardModifier() {
    override fun onUse(card: AbstractCard?, target: AbstractCreature?, action: UseCardAction?) {
        drawCard(amount)
    }

    private val cardString: CardStrings = CardCrawlGame.languagePack.getCardStrings(DrawCardMod::class.makeId())
    val s = cardString.DESCRIPTION.format(amount)

    override fun removeOnCardPlayed(card: AbstractCard?): Boolean {
        return true
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            rawDescription = rawDescription.replace(s, "")
            initializeDescription()
        }
    }

    override fun makeCopy(): AbstractCardModifier {
        return DrawCardMod()
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription $s"
    }
}