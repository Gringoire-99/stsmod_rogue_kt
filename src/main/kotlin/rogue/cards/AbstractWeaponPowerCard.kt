package rogue.cards

import rogue.characters.RogueEnum
import kotlin.properties.Delegates

abstract class AbstractWeaponPowerCard(
    name: String,
    cost: Int,
    rarity: CardRarity,
    val initialDamage: Int,
    val initialDuration: Int,
    type: CardType = CardType.POWER,
    color: CardColor = RogueEnum.HS_ROGUE_CARD_COLOR
) : AbstractRogueCard(
    name, cost, type = type,
    rarity,
    target = CardTarget.SELF,
    color = color
) {

    var isWeaponDamageModified: Boolean = false
    var isWeaponDurationModified: Boolean = false
    var weaponDamage: Int by Delegates.observable(initialDamage) { _, old, new ->
        if (new > old) {
            isWeaponDamageModified = true
        }
    }
    var weaponDuration: Int by Delegates.observable(initialDuration) { _, old, new ->
        if (new > old) {
            isWeaponDurationModified = true
        }
    }


}