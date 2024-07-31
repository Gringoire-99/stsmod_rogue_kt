package rogue.mods

import basemod.abstracts.AbstractCardModifier
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.action.EmptyAction
import rogue.cards.skill.RollTheBones
import utils.makeId
import utils.removeMod

class FateMod(val copy: AbstractCard = RollTheBones().apply { purgeOnUse = true }) : AbstractCardModifier(), OnDiscard {
    companion object {
        val id = FateMod::class.makeId()
        val s = " NL *命运"
    }

    override fun onExhausted(card: AbstractCard?) {
        card?.let {
            addToBot(EmptyAction {
                AbstractDungeon.actionManager.addCardQueueItem(
                    CardQueueItem(
                        copy.makeStatEquivalentCopy(),
                        true,
                        0,
                        true,
                        true
                    )
                )
                it.removeMod(true, id)
            })
        }
    }

    override fun modifyDescription(rawDescription: String?, card: AbstractCard?): String {
        return "$rawDescription$s"
    }

    override fun onRemove(card: AbstractCard?) {
        card?.apply {
            rawDescription = rawDescription.replace(s, "")
            initializeDescription()
        }
    }

    override fun identifier(card: AbstractCard?): String {
        return id
    }

    override fun makeCopy(): AbstractCardModifier {
        return FateMod()
    }

    override fun onManualDiscard(target: AbstractCard) {
        target.let {
            addToBot(EmptyAction {
                AbstractDungeon.actionManager.addCardQueueItem(
                    CardQueueItem(
                        copy.makeStatEquivalentCopy(),
                        true,
                        0,
                        true,
                        true
                    )
                )
                it.removeMod(true, id)
            })
        }
    }
}