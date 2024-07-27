package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard

class QuickPick(initialDamage: Int = 2, initialDuration: Int = 6, magic: Int = 1) :
    AbstractWeaponPowerCard(
        name = QuickPick::class.simpleName.toString(),
        cost = 0,
        rarity = CardRarity.COMMON, initialDamage, initialDuration,
    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDamage++
            weaponDuration += 2
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.QuickPick(
                    damage = weaponDamage,
                    duration = weaponDuration,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}