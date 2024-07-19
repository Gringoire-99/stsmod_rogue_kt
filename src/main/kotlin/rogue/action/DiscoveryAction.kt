package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.*
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.screens.CardRewardScreen
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect
import utils.generateCardChoices

class DiscoveryAction
    (private val cardFilter: CardFilter = CardFilter(), val cb: (AbstractCard) -> Unit = {}) : AbstractGameAction() {
    private var retrieveCard = false

    init {
        this.actionType = ActionType.CARD_MANIPULATION
        this.duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(
                generateCardChoices(cardFilter),
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
                disCard.current_x = -1000.0f * Settings.xScale
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(
                        ShowCardAndAddToHandEffect(
                            disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f
                        )
                    )
                } else {
                    AbstractDungeon.effectList.add(
                        ShowCardAndAddToDiscardEffect(
                            disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f
                        )
                    )
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null
            }

            this.retrieveCard = true
        }

        tickDuration()
    }


}

class CardFilter(
    val isUpgraded: Boolean = false,
    val cardType: HashSet<CardType> = hashSetOf(CardType.SKILL, CardType.POWER, CardType.ATTACK),
    val cardRarity: HashSet<CardRarity> = hashSetOf(CardRarity.COMMON, CardRarity.UNCOMMON, CardRarity.RARE),
    val cardColor: HashSet<CardColor> = hashSetOf(CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.PURPLE),
    val predicate: ((card: AbstractCard) -> Boolean)? = null,
    val costFilter: (Int) -> Boolean = { true }
)