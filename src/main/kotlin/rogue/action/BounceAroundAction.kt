package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import rogue.cards.skill.BounceAround
import utils.getRandomMonster
import kotlin.math.min

class BounceAroundAction(val upgraded: Boolean = false) : AbstractGameAction() {
    override fun update() {
        val cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn.filter {
            it !is BounceAround && !it.purgeOnUse && it.costForTurn != -2
        }.map {
            it.makeStatEquivalentCopy().apply { purgeOnUse = true }
        }
        val groupBy = cardsPlayedThisTurn.groupBy {
            if (it.freeToPlayOnce) 0 else it.costForTurn
        }.toSortedMap()
        val xCards = groupBy.remove(-1)
        groupBy.forEach {
            it.value.forEach { c ->
                play(c, if (upgraded) min(1, it.key) else it.key)
            }
        }
        xCards?.forEach {
            play(it, -1)
        }
        isDone = true
    }

    private fun play(c: AbstractCard, use: Int) {
        addToBot(EmptyAction {
            val m = getRandomMonster()
            if (c.cardPlayable(m) && (c.freeToPlayOnce || use == -1 || use <= EnergyPanel.getCurrentEnergy())) {
                AbstractDungeon.actionManager.addCardQueueItem(
                    CardQueueItem(c, true, 0, true, true),
                )
                if (use != -1) {
                    EnergyPanel.useEnergy(use)
                } else {
                    EnergyPanel.setEnergy(0)
                }
            }
        })
    }
}

