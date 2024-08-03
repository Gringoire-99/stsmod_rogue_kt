package rogue.cards.power

import basemod.cardmods.EtherealMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import utils.addMod
import utils.removeMod

class PerditionsBlade(
    wDamage: Int = 4,
    wDurability: Int = 3,
    magic: Int = 3,
) :
    AbstractWeaponPowerCard(
        name = PerditionsBlade::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        initialDamage = wDamage,
        initialDurability = wDurability
    ) {
    init {
        setMagicNumber(magic)
        addMod(EtherealMod())
        removeMod(true, RetainMod.ID)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDurability++
            weaponDamage++
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.PerditionsBlade(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            )
        )
    }
}