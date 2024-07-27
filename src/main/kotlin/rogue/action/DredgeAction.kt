package rogue.action

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DredgeAction(val cb: (AbstractCard) -> Unit = {}, val magic: Int = 4) : AbstractGameAction() {
    override fun update() {
        val last = ArrayList(AbstractDungeon.player.drawPile.group.take(magic))
        if (last.isNotEmpty()) {
            addToTop(SelectCardsAction(
                last, "选择一张放到抽牌堆顶部"
            ) { cards ->
                cards.forEach {
                    cb(it)
                    AbstractDungeon.player.drawPile.removeCard(it)
                    AbstractDungeon.player.drawPile.addToTop(it)
                }
            })
        }
        isDone = true
    }
}