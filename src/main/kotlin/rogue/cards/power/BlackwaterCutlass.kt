package rogue.cards.power

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import rogue.cards.Tradeable
import rogue.mods.ReduceCostMod
import utils.addMod
import utils.discovery

class BlackwaterCutlass(
    initialDamage: Int = 3, initialDurability: Int = 3, magic: Int = 2,
    override var isEnableTrade: Boolean = true
) :
    AbstractWeaponPowerCard(
        name = BlackwaterCutlass::class.simpleName.toString(),
        cost = 0,
        type = CardType.POWER,
        rarity = CardRarity.COMMON, initialDamage = initialDamage, initialDurability = initialDurability
    ), Tradeable {
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
                rogue.power.weapon.BlackwaterCutlass(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    upgraded = upgraded
                )
            )
        )
    }

    override fun onTrade() {
        weaponDamage += magicNumber
        weaponDurability += magicNumber
        discovery {
            it.addMod(EtherealMod(), ExhaustMod())
            if (upgraded) {
                it.addMod(ReduceCostMod(1, isTurnEffect = true))
            }
        }
    }
}