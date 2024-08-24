package rogue.action

import basemod.BaseMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.mods.NecriumMod
import utils.addToQueue

class MyrasUnstableElementAction(val p: AbstractPlayer) : AbstractGameAction() {
    override fun update() {
        val toDraw = BaseMod.MAX_HAND_SIZE - p.hand.size()
        if (toDraw > 0) {
            addToTop(DrawCardAction(toDraw))
            addToBot(EmptyAction {
                p.drawPile.group.forEach {
                    val hasModifier = CardModifierManager.hasModifier(it, NecriumMod.id)
                    if (hasModifier) {
                        addToQueue(it.makeStatEquivalentCopy(), null, random = true, purge = true)
                    }
                    addToTop(ExhaustSpecificCardAction(it, p.drawPile, true))
                }
            })
        }
        isDone = true
    }
}