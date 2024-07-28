package rogue.cards.impls

import rogue.cards.LevelInterface
import kotlin.math.max


class LevelInterfaceImpl(
    maxExpr: Int,
    maxLevel1: Int,
    val currentLevel: Int = 1,
    currentExp: Int = 0,
    var onMaxExpCb: () -> Unit = {},
    var onLevelUpCb: () -> Unit = {},
    var onMaxLevelCb: () -> Unit = {}
) :
    LevelInterface {
    override val maxExp: Int = max(0, maxExpr)
    override var exp: Int = max(0, currentExp)
        set(value) {
//            当小于等于最大等级时经验才会发生变化
            if (level <= maxLevel && value >= 0) {
                field = value
//                小于最大等级且达到最大经验才能升级
                if (field >= maxExp) {
                    onMaxExp()
                    levelUp()
                }
            }
        }
    override var level: Int = max(0, currentLevel)
        set(value) {
//            0到最大等级是有效的变化
            if (value in 0..maxLevel) {
//                等级提高时触发升级的函数
                val isLevelUp = value > field
                field = value
                if (isLevelUp && !tempDisableHooks) {
                    onLevelUp()
                }
                if (field == maxLevel && !tempDisableHooks) {
                    onMaxLevel()
                }
            }
        }
    override val maxLevel: Int = max(level + 1, maxLevel1)

    private var tempDisableHooks = false
    private fun levelUp() {
        level++
        exp -= maxExp
    }

    override fun onMaxExp() {
        onMaxExpCb()
    }

    override fun onLevelUp() {
        onLevelUpCb()
    }

    override fun onMaxLevel() {
        onMaxLevelCb()
    }

    override fun setInitialLevel(level: Int) {
        tempDisableHooks = true
        this.level = level
        tempDisableHooks = false
    }

    override fun resetLevel() {
        tempDisableHooks = true
        this.level = currentLevel
        tempDisableHooks = false
    }

}