package rogue.cards.power

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import utils.addMod

class QuickPick(initialDamage: Int = 2, initialDurability: Int = 6, magic: Int = 1) :
    AbstractWeaponPowerCard(
        name = QuickPick::class.simpleName.toString(),
        cost = 0,
        rarity = CardRarity.COMMON, initialDamage, initialDurability,
    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            addMod(InnateMod())
            weaponDamage++
            weaponDurability += 2
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.QuickPick(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}