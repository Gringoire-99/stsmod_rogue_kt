package rogue.power.quest

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.helpers.FontHelper
import rogue.power.IAbstractPower
import utils.modId

abstract class AbstractQuestPower(owner: AbstractCreature, rawName: String) :
    IAbstractPower(
        powerName = rawName,
        owner = owner,
        imgPath = "$modId/powers/${AbstractQuestPower::class.simpleName.toString()}",
        amount = 0
    ), NonStackablePower {
    abstract val maxCount: Int

    init {
        updateDescription()
    }

    var questCount = 0
        set(value) {
            field = value
            flash()
            if (field >= maxCount) {
                onComplete()
                addToBot(RemoveSpecificPowerAction(owner, owner, this))
                field = 0
            }
            amount = questCount
        }

    override fun renderAmount(sb: SpriteBatch?, x: Float, y: Float, c: Color?) {
        FontHelper.renderFontCentered(
            sb,
            FontHelper.powerAmountFont,
            "${amount}/${maxCount}",
            x,
            y,
            Color.GREEN.cpy()
        )

    }


    abstract fun onComplete()
    final override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(maxCount)
        name = powerString.NAME
    }
}