package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class Shadowblade(initialDamage: Int = 3, initialDurability: Int = 3, magic: Int = 2) :
    AbstractWeaponPowerCard(
        name = Shadowblade::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON, initialDurability = initialDurability, initialDamage = initialDamage
    ) {

    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
            weaponDamage++
            weaponDurability++
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.Shadowblade(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}