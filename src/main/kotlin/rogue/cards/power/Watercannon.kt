package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import utils.upMagicNumber

class Watercannon(wDamage: Int = 2, wDurability: Int = 2, val magic: Int = 3) :
    AbstractWeaponPowerCard(
        name = Watercannon::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        initialDamage = wDamage,
        initialDurability = wDurability,
    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDamage++
            weaponDurability++
            upMagicNumber(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.Watercannon(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magicNumber = magicNumber
                )
            )
        )
    }
}