package rogue.cards

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.characters.RogueEnum
import utils.addMod
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
    init {
        addMod(RetainMod())
    }

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

    override fun makeStatEquivalentCopy(): AbstractCard {
        val c = super.makeStatEquivalentCopy() as AbstractWeaponPowerCard
        c.let {
            it.isWeaponDamageModified = isWeaponDamageModified
            it.isWeaponDurationModified = isWeaponDurationModified
            it.weaponDamage = weaponDamage
            it.weaponDuration = weaponDuration
        }
        return c
    }

}