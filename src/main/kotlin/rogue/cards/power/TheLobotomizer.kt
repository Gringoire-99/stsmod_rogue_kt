package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class TheLobotomizer(initialDamage: Int = 3, initialDuration: Int = 3, magic: Int = 20) :
    AbstractWeaponPowerCard(
        name = TheLobotomizer::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.UNCOMMON, initialDuration = initialDuration, initialDamage = initialDamage
    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(13)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.TheLobotomizer(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}