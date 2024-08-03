package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class WickedKnife(initialDamage: Int = 3, initialDurability: Int = 3) :
    AbstractWeaponPowerCard(
        name = WickedKnife::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.SPECIAL, initialDamage, initialDurability, color = CardColor.COLORLESS
    ) {


    override fun upgrade() {
        useUpgrade {
            weaponDamage++
            weaponDurability++
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.WickedKnife(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    upgraded = upgraded
                )
            )
        )
    }
}