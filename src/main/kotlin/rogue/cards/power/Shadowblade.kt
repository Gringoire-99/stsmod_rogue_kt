package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class Shadowblade(initialDamage: Int = 4, initialDuration: Int = 2, magic: Int = 4) :
    AbstractWeaponPowerCard(
        name = Shadowblade::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON, initialDuration = initialDuration, initialDamage = initialDamage
    ) {

    init {

        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(3)
            weaponDamage++
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.Shadowblade(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}