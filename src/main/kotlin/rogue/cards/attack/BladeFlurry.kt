package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import rogue.power.weapon.AbstractWeaponPower
import utils.attackWithWeapon
import utils.getWeaponPower

class BladeFlurry() :
    AbstractWeaponCard(
        name = BladeFlurry::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ALL_ENEMY,
    ) {
    init {
        setMagicNumber(3)
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        var count = 0
        p?.apply {
            getWeaponPower()?.apply {
                count = if (duration > magicNumber) magicNumber else duration
            }
        }
        repeat(count) {
            p?.attackWithWeapon(damage = damage, loseDuration = 1)
        }
    }
}