package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class NecriumBlade(damage: Int = 3, duration: Int = 3) :
    AbstractWeaponPowerCard(
        name = NecriumBlade::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON, initialDamage = damage, initialDuration = duration
    ) {


    override fun upgrade() {
        useUpgrade {
            weaponDamage++
            weaponDuration++

        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.NecriumBlade(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    upgraded = upgraded
                )
            )
        )
    }
}