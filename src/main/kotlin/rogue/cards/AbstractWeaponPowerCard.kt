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
    val initialDurability: Int,
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
    var isWeaponDurabilityModified: Boolean = false
    var weaponDamage: Int by Delegates.observable(initialDamage) { _, old, new ->
        if (new > old) {
            isWeaponDamageModified = true
        }
    }
    var weaponDurability: Int by Delegates.observable(initialDurability) { _, old, new ->
        if (new > old) {
            isWeaponDurabilityModified = true
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val c = super.makeStatEquivalentCopy() as AbstractWeaponPowerCard
        c.let {
            it.isWeaponDamageModified = isWeaponDamageModified
            it.isWeaponDurabilityModified = isWeaponDurabilityModified
            it.weaponDamage = weaponDamage
            it.weaponDurability = weaponDurability
        }
        return c
    }

}