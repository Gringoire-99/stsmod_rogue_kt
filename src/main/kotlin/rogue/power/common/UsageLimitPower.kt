package rogue.power.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.helpers.FontHelper
import rogue.power.IAbstractPower

abstract class UsageLimitPower(owner: AbstractCreature, stackAmount: Int = 1, rawName: String) :
    IAbstractPower(powerName = rawName, owner = owner, amount = stackAmount) {
    var useCount = 0

    init {
        useCount += stackAmount
        (owner.getPower(this.ID) as? UsageLimitPower)?.let {
            it.useCount += stackAmount
            it.updateDescription()
        }
    }


    override fun atStartOfTurnPostDraw() {
        (owner as? AbstractPlayer)?.apply {
            useCount = owner.getPower(this@UsageLimitPower.ID).amount
        }
    }

    fun usePower(cb: () -> Unit) {
        if (useCount > 0) {
            cb()
            useCount--
        }
    }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            "${useCount}/${amount}",
            x,
            y,
            Color.GREEN.cpy()
        )
    }
}