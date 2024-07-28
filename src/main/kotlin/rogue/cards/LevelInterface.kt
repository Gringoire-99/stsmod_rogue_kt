package rogue.cards

interface LevelInterface {
    var exp: Int
    val maxExp: Int
    var level: Int
    val maxLevel: Int
    fun onMaxExp()
    fun onLevelUp()
    fun onMaxLevel()
    fun setInitialLevel(level: Int)
    fun resetLevel()
}