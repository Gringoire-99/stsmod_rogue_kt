package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.Tradeable

class PlayTwiceAction(val card: AbstractCard, val target: AbstractCreature?) : AbstractGameAction() {
    override fun update() {
        val tmp: AbstractCard = card.makeSameInstanceOf()
        if(tmp is Tradeable){
            tmp.isEnableTrade = false
        }
        AbstractDungeon.player.limbo.addToBottom(tmp)
        tmp.current_x = card.current_x
        tmp.current_y = card.current_y
        tmp.target_x = Settings.WIDTH.toFloat() / 2.0f - 300.0f * Settings.scale
        tmp.target_y = Settings.HEIGHT.toFloat() / 2.0f
        val m = if (target is AbstractMonster) target as AbstractMonster else null
        if (target is AbstractMonster) {
            tmp.calculateCardDamage(target as AbstractMonster)
        }

        tmp.purgeOnUse = true
        AbstractDungeon.actionManager.addCardQueueItem(
            CardQueueItem(tmp, m, card.energyOnUse, true, true),
        )
        isDone = true
    }
}