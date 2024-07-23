package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import rogue.mods.ReduceCostMod
import rogue.power.weapon.AbstractWeaponPower
import utils.addMod
import utils.getWeaponPower

class Doomerang :
    AbstractWeaponCard(
        name = Doomerang::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        addMod(RetainMod())
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.getWeaponPower()?.apply {
            attack(target = m, damage = this@Doomerang.damage)
            p.powers.removeIf {
                it.ID == AbstractWeaponPower.id
            }
            val copy = makeCopy()
            if (this@Doomerang.upgraded) {
                copy.addMod(ReduceCostMod(1))
            }
            p.hand.addToHand(copy)

        }
    }
}