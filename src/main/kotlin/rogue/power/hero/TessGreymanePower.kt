package rogue.power.hero

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.helpers.FontHelper
import common.CardFilter
import common.ScavengeCard
import common.TradeCard
import rogue.cards.AbstractRogueCard
import rogue.mods.TempCardMod
import rogue.power.common.PlayTwicePower
import utils.addMod
import utils.discovery
import utils.upBase
import kotlin.math.min

class TessGreymanePower(owner: AbstractPlayer) :
    AbstractHeroPower(owner = owner, powerName = TessGreymanePower::class.simpleName.toString(), stackAmount = 1) {

    private val maxUsage = 2
    override val ability = {
        if (amount > 0) {
            amount--
            this@TessGreymanePower.flash()
            val opts = arrayListOf<AbstractCard>(ScavengeCard(0), ScavengeCard(1), ScavengeCard(2))
            addToBot(SelectCardsAction(opts, TradeCard.tradeStrings.DESCRIPTION) { cards ->
                val first = cards.firstOrNull() as? ScavengeCard
                when (first?.opt) {
                    0 -> {
                        discovery {
                            it.upBase(2)
                            it.addMod(TempCardMod())
                        }
                    }
                    1 -> {
                        addToBot(ApplyPowerAction(owner, owner, PlayTwicePower(owner, 1), 1))
                    }

                    2 -> {
                        val from = ArrayList(AbstractRogueCard.cardUsedCombatOC)
                        discovery(from = from, cardFilter = CardFilter(predicate = { true })) {
                            if (it.cost > 0) {
                                it.cost = 0
                                it.costForTurn = 0
                            }
                            it.addMod(TempCardMod())
                        }
                    }
                }

            })
        }
    }

    override fun atStartOfTurnPostDraw() {
        amount = min(maxUsage, amount + 1)
    }

    override val afterApply = {
        getBaseAbility()?.rightClick = ability
    }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            "${amount}/${maxUsage}",
            x,
            y,
            Color.GREEN.cpy()
        )
    }

}