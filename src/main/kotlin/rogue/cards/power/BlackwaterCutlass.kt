package rogue.cards.power

import basemod.cardmods.EtherealMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import rogue.cards.Tradeable
import utils.addMod
import utils.discovery

class BlackwaterCutlass(
    initialDamage: Int = 3, initialDuration: Int = 3, magic: Int = 1,
    override var isEnableTrade: Boolean = true
) :
    AbstractWeaponPowerCard(
        name = BlackwaterCutlass::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.COMMON, initialDamage = initialDamage, initialDuration = initialDuration
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
                    duration = weaponDuration,
                    upgraded = upgraded
                )
            )
        )
    }

    override fun onTrade() {
        weaponDamage += magicNumber
        weaponDuration += magicNumber
        discovery {
            it.addMod(EtherealMod())
            if (upgraded) {
                it.costForTurn = if (it.costForTurn - 1 <= 0) 0 else it.costForTurn - 1
            }
        }
    }
}