package rogue.cards

interface LevelInterface {
    var exp: Int
    val maxExp: Int
    var level: Int
    val maxLevel: Int
    fun onMaxExp() {
        onMaxExpCb()
    }

    fun onLevelUp() {
        onLevelUpCb()
    }

    fun onMaxLevel() {
        onMaxLevelCb()
    }

    var onMaxExpCb: () -> Unit
    var onLevelUpCb: () -> Unit
    var onMaxLevelCb: () -> Unit
    fun setInitialLevel(level: Int)
    fun resetLevel()
    fun isMaxLevel(): Boolean {
        return maxLevel == level
    }
}