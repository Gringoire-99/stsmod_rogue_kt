package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class WickedKnife(initialDamage: Int = 3, initialDuration: Int = 3) :
    AbstractWeaponPowerCard(
        name = WickedKnife::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.SPECIAL, initialDamage, initialDuration,
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
                rogue.power.weapon.WickedKnife(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    upgraded = upgraded
                )
            )
        )
    }
}