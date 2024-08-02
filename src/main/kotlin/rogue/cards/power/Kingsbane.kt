package rogue.cards.power

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import utils.addMod

class Kingsbane(wDamage: Int = 3, wDuration: Int = 3, val magic: Int = 2) :
    AbstractWeaponPowerCard(
        name = Kingsbane::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.RARE,
        initialDamage = wDamage,
        initialDuration = wDuration,
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
        weaponDuration = copy.duration
        weaponDamage = copy.damage
        if (copy.damage > initialDamage) {
            isWeaponDamageModified = true
        }
        if (copy.duration > initialDuration) {
            isWeaponDurationModified = true
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
            duration = initialDuration,
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