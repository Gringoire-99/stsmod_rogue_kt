package rogue.action

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class StunAllMonsterAction(val s: AbstractCreature) : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.getMonsters().monsters?.forEach {
            addToTop(StunMonsterAction(it, s))
        }
        isDone = true
    }
}