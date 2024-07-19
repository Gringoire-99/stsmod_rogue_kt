package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class AssassinsBlade(
    wDuration: Int = 5,
    wDamage: Int = 3,
    magic: Int = 4
) :
    AbstractWeaponPowerCard(
        name = AssassinsBlade::class.simpleName.toString(),
        cost = 2,
        rarity = CardRarity.COMMON,
        initialDuration = wDuration,
        initialDamage = wDamage

    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDamage+=2
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.AssassinsBlade(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}