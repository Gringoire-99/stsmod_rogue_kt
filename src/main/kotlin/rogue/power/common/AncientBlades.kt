package rogue.power.common

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower
import rogue.action.GetUniqueCardAction
import rogue.cards.attack.MirageBlade
import rogue.power.IAbstractPower
import rogue.power.weapon.AbstractWeaponPower
import utils.getWeaponPower

class AncientBlades(owner: AbstractPlayer) :
    IAbstractPower(powerName = AncientBlades::class.simpleName.toString(), owner = owner, amount = -1) {

    init {
        (owner as? AbstractPlayer).apply {
            this?.getWeaponPower()?.apply {
                damage += 2
                durability += 2
            }
        }
    }

    override fun onApplyPower(power: AbstractPower?, target: AbstractCreature?, source: AbstractCreature?) {
        (power as? AbstractWeaponPower)?.apply {
            damage += 2
            durability += 2
        }
    }

    override fun atStartOfTurn() {
        addToBot(GetUniqueCardAction(MirageBlade()))
    }

}