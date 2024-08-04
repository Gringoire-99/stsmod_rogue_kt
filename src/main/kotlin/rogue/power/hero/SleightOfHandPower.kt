package rogue.power.hero

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.helpers.FontHelper
import rogue.action.ReduceAllCostInHandAction
import kotlin.math.min

class SleightOfHandPower(owner: AbstractPlayer) :
    AbstractHeroPower(powerName = SleightOfHandPower::class.simpleName.toString(), owner = owner, stackAmount = 1) {
    val maxUsage = 2
    override val ability = {
        if (amount > 0) {
            amount--
            addToBot(ReduceAllCostInHandAction())
            this@SleightOfHandPower.flash()
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