package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class AssassinsBlade(
    wDurability: Int = 5,
    wDamage: Int = 3,
    magic: Int = 6
) :
    AbstractWeaponPowerCard(
        name = AssassinsBlade::class.simpleName.toString(),
        cost = 2,
        rarity = CardRarity.COMMON,
        initialDurability = wDurability,
        initialDamage = wDamage

    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDamage+=2
            upgradeMagicNumber(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.AssassinsBlade(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}