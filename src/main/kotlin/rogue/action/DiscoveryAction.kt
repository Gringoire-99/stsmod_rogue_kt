package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.screens.CardRewardScreen
import common.CardFilter
import utils.generateCardChoices

class DiscoveryAction
    (
    private val cardFilter: CardFilter = CardFilter(),
    val isUpgraded: Boolean = false,
    val cb: (AbstractCard) -> Unit = {}
) : AbstractGameAction() {
    private var retrieveCard = false

    init {
        this.actionType = ActionType.CARD_MANIPULATION
        this.duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(
                ArrayList(generateCardChoices(cardFilter, number = 4, upgraded = isUpgraded).take(4)),
                CardRewardScreen.TEXT[1],
                true
            )
            tickDuration()
            return
        }

        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                val disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy()
                cb(disCard)
                addToBot(MakeTempCardInHandAction(disCard))
                AbstractDungeon.cardRewardScreen.discoveryCard = null
            }

            this.retrieveCard = true
        }

        tickDuration()
    }


}
