package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class SpectralCutlass(initialDamage: Int = 3, initialDuration: Int = 3, magic: Int = 1) :
    AbstractWeaponPowerCard(
        name = SpectralCutlass::class.simpleName.toString(),
        cost = 2,
        type = CardType.POWER,
        rarity = CardRarity.RARE, initialDuration = initialDuration, initialDamage = initialDamage
    ) {

    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.SpectralCutlass(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}