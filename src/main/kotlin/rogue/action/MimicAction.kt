package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.cards.skill.MimicPod
import utils.mimic
import kotlin.random.Random

class MimicAction(val card: MimicPod) : AbstractGameAction() {
    override fun update() {
        card.apply {
            val g = AbstractDungeon.player.hand.group
            val indexOfOld = g.indexOf(this)
            if (indexOfOld == -1) {
                return
            }
            var pos = indexOfOld
            val nextBoolean = Random.nextBoolean()
            if (pos + 1 == g.size) {
                pos -= 1
            } else if (pos == 0) {
                pos += 1
            } else {
                pos = if (nextBoolean) pos + 1 else pos - 1
            }

            val target = g[pos].makeStatEquivalentCopy()
            this.mimic(target)
        }
        isDone = true
    }
}