package rogue.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.SpiritOfTheSharkPower
import utils.makeId

abstract class AbstractComboCard(name: String, cost: Int, type: CardType, rarity: CardRarity, target: CardTarget) :
    AbstractRogueCard(
        name, cost, type,
        rarity,
        target,
    ) {
    var isEnableComboEffect = false

    /**
     * 可以触发连击效果时，发光
     */
    override fun triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy()
        if (isComboOn) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy()
        }

    }

    /**
     * 在使用时判断连击是否开启，如果有开启则消耗连击并获得连击效果
     */

    fun useCombo(cb: () -> Unit = {}): Boolean {
        if (isComboOn) {
            isComboOn = false
            comboCount++
            isEnableComboEffect = true
            cb()
            AbstractDungeon.player?.apply {
                if (hasPower(SpiritOfTheSharkPower::class.makeId())) {
                    cb()
                    comboCount++
                }
            }
        }
        return isEnableComboEffect
    }

    /**
     * 使用一张卡后开启连击效果，触发连击效果后关闭
     */
    companion object {
        var isComboOn = false
        var comboCount = 0
        fun reset() {
            isComboOn = false
            comboCount = 0
        }
    }

}