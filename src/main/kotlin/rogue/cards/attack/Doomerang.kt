package rogue.cards.attack

import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import rogue.power.weapon.AbstractWeaponPower
import utils.getWeaponPower

class Doomerang() :
    AbstractWeaponCard(
        name = Doomerang::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        CardModifierManager.addModifier(this, RetainMod())
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.getWeaponPower()?.apply {
            attack(target = m, damage = damage)
            p.powers.removeIf {
                it.ID == AbstractWeaponPower.id
            }
            val copy = makeCopy()
            if (this@Doomerang.upgraded) {
                copy.cost = if (copy.cost - 1 < 0) 0 else copy.cost - 1
                copy.costForTurn = copy.cost
                CardModifierManager.addModifier(copy, RetainMod())
            }
            p.hand.addToHand(copy)

        }
    }
}