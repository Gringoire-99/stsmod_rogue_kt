package rogue.cards.power

import basemod.cardmods.InnateMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class Kingsbane(wDamage: Int = 3, wDuration: Int = 3) :
    AbstractWeaponPowerCard(
        name = Kingsbane::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.RARE,
        initialDamage = wDamage,
        initialDuration = wDuration,
    ) {
    var copy: rogue.power.weapon.Kingsbane? = null
    fun setWeaponCopy(copy: rogue.power.weapon.Kingsbane) {
        copy.duration = copy.initialDuration + copy.additionalDuration
        this.copy = copy
        weaponDuration = copy.duration
        weaponDamage = copy.damage
        utils.logger.info("copyed duration ${copy.duration} damage ${copy.damage} $copy")
        if (copy.damage > initialDamage) {
            isWeaponDamageModified = true
        }
        if (copy.duration > initialDuration) {
            isWeaponDurationModified = true
        }
    }


    override fun upgrade() {
        useUpgrade {
            CardModifierManager.addModifier(this,InnateMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val kingsbane = copy ?: rogue.power.weapon.Kingsbane(
            damage = initialDamage,
            duration = initialDuration,
            upgraded = upgraded
        )
        addToBot(
            EquipWeaponAction(
                kingsbane
            )
        )

    }
}