package rogue.cards

interface UpgradeInterface {
    var upgradeCount: Int
    val maxUpgradeCount: Int
    var level: Int
    fun onMaxUpgrade()
}