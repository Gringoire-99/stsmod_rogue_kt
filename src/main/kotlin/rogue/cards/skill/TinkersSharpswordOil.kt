package rogue.cards.skill

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractComboCard
import utils.getWeaponPower
import utils.isWeaponEquipped

class TinkersSharpswordOil :
    AbstractComboCard(
        name = TinkersSharpswordOil::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        type = CardType.SKILL,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        var addDamage = magicNumber
        var addDurability = 1
        useCombo {
            addDamage += addDamage
            addDurability += addDurability
        }
        addToBot(EmptyAction {
            p?.getWeaponPower()?.apply {
                damage += addDamage
                durability += addDurability
                updatePowerDesc()
            }
        })

    }
}