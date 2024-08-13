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
        cost = 0,
        rarity = CardRarity.RARE,
        initialDamage = wDamage,
        initialDurability = wDurability,
    ) {
    init {
        setMagicNumber(magic)
    }

    private val initializerName: String = name
    override fun makeStatEquivalentCopy(): AbstractCard {
        val c = super.makeStatEquivalentCopy() as Kingsbane
        copy?.apply {
            c.setWeaponCopy(this)
        }
        return c
    }

    var copy: rogue.power.weapon.Kingsbane? = null

    fun setWeaponCopy(copy: rogue.power.weapon.Kingsbane) {
        this.copy = copy
        weaponDurability = copy.durability
        weaponDamage = copy.damage
        setMagicNumber(copy.magic)
        timesUpgraded = copy.upgradeCount
        if (copy.upgraded) {
            upgraded = true
            updateName()
            addMod(InnateMod())
        }
        if (copy.damage > initialDamage) {
            isWeaponDamageModified = true
        }
        if (copy.durability > initialDurability) {
            isWeaponDurabilityModified = true
        }
    }


    override fun upgrade() {
        upgradeName()
        addMod(InnateMod())
        upgradeMagicNumber(3)
        weaponDamage++
    }

    override fun upgradeName() {
        timesUpgraded++
        upgraded = true
        updateName()
    }

    private fun updateName() {
        this.name = "${initializerName}+${timesUpgraded}"
        initializeTitle()
    }

    override fun canUpgrade(): Boolean {
        return true
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val kingsbane = copy ?: rogue.power.weapon.Kingsbane(
            damage = weaponDamage,
            durability = weaponDurability,
            upgraded = upgraded,
            magic = magicNumber, timesUpgraded
        )
        addToBot(
            EquipWeaponAction(
                kingsbane
            )
        )

    }
}