package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.cards.skill.MimicPod
import kotlin.random.Random

class MimicPodAction(val card: MimicPod) : AbstractGameAction() {
    override fun update() {
        card.apply {
            val g = AbstractDungeon.player.hand.group
            val indexOfOld = g.indexOf(this)
            if (indexOfOld == -1 || g.size <= 1) {
                isDone = true
                return
            }
            var pos = indexOfOld
            val nextBoolean = Random.nextBoolean()

            when (pos) {
                g.size - 1 -> {
                    pos -= 1
                }

                0 -> {
                    pos += 1
                }

                else -> {
                    if (g.size >= 3) {
                        pos = if (nextBoolean) pos + 1 else pos - 1
                    }
                }
            }

            val target = g[pos].makeStatEquivalentCopy()
            this.mimic(target)
        }
        isDone = true
    }
}