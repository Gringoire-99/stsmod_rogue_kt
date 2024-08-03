package rogue.cards.power

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import utils.addMod

class Kingsbane(wDamage: Int = 3, wDurability: Int = 3, val magic: Int = 2) :
    AbstractWeaponPowerCard(
        name = Kingsbane::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.RARE,
        initialDamage = wDamage,
        initialDurability = wDurability,
    ) {
    init {
        setMagicNumber(magic)
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val c = super.makeStatEquivalentCopy() as Kingsbane
        copy?.apply {
            c.setWeaponCopy(this)
        }
        return c
    }

    private var copy: rogue.power.weapon.Kingsbane? = null
    fun setWeaponCopy(copy: rogue.power.weapon.Kingsbane) {
        this.copy = copy
        weaponDurability = copy.durability
        weaponDamage = copy.damage
        if (copy.damage > initialDamage) {
            isWeaponDamageModified = true
        }
        if (copy.durability > initialDurability) {
            isWeaponDurabilityModified = true
        }
    }


    override fun upgrade() {
        useUpgrade {
            addMod(InnateMod())
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val kingsbane = copy ?: rogue.power.weapon.Kingsbane(
            damage = initialDamage,
            durability = initialDurability,
            upgraded = upgraded,
            magic = magicNumber
        )
        addToBot(
            EquipWeaponAction(
                kingsbane
            )
        )

    }
}