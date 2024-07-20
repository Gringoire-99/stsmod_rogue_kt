package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import utils.drawCard

class DrawCardMod(val amount: Int = 1) : AbstractCardModifier() {
    override fun onUse(card: AbstractCard?, target: AbstractCreature?, action: UseCardAction?) {
        drawCard(amount)
    }

    override fun makeCopy(): AbstractCardModifier {
        return DrawCardMod()
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription NL *使用后抽${amount}张牌"
    }
}