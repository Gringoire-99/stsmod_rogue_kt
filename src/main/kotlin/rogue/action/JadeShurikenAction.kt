package rogue.action

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.Mimicable
import rogue.cards.attack.JadeShuriken
import utils.addToQueue
import utils.makeId

class JadeShurikenAction(val p: AbstractPlayer, val m: AbstractMonster, val card: AbstractCard) : AbstractGameAction() {
    override fun update() {
        val cards = p.hand.group.filter {
            it.cardID == JadeShuriken::class.makeId() || (it is Mimicable && it.targetCard?.cardID == JadeShuriken::class.makeId())
        }

        cards.forEach { _ ->
            addToTop(
                MoveCardsAction(
                    AbstractDungeon.player.limbo,
                    AbstractDungeon.player.hand,
                    { card: AbstractCard ->
                        card in cards
                    },
                    cards.size
                ) {
                    it.forEach { c ->
                        c.addToQueue(card, m, random = true) {
                            MoveCardsAction(
                                AbstractDungeon.player.discardPile, AbstractDungeon.player.limbo,
                                { card: AbstractCard ->
                                    card in cards
                                }, cards.size
                            )
                        }
                    }
                }
            )
        }
        isDone = true
    }
}