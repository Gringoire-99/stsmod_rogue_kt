package rogue.cards.skill

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower

class DeadlyPoison :
    AbstractRogueCard(
        name = DeadlyPoison::class.simpleName.toString(),
        cost = 0,
        rarity = CardRarity.COMMON, type = CardType.SKILL,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(4)
        ExhaustiveVariable.setBaseValue(this, 2)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        val canUse = super.canUse(p, m)
        if (!canUse) {
            return false
        }
        if (!isWeaponEquipped()) {
            cantUseMessage = needMessage
            return false
        }
        return true
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            p?.getWeaponPower()?.let {
                it.damage += magicNumber
            }
        })
    }
}