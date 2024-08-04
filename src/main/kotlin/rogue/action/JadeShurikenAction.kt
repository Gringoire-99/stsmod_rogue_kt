package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.attack.JadeShuriken
import utils.addToQueue
import utils.makeId

class JadeShurikenAction(val p: AbstractPlayer, val m: AbstractMonster, val card: AbstractCard) : AbstractGameAction() {
    override fun update() {
        val cards = p.hand.group.filter {
            it.cardID == JadeShuriken::class.makeId()
        }
        AbstractDungeon.player.hand.group.removeIf { it.cardID == JadeShuriken::class.makeId() }
        cards.forEach {
            addToQueue(it, m, random = true)
        }
        isDone = true
    }
}