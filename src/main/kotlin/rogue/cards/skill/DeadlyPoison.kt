package rogue.cards.skill

import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower
import utils.isWeaponEquipped

class DeadlyPoison() :
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
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun upgrade() {
        useUpgrade {
            CardModifierManager.addModifier(this, RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        utils.logger.info(p?.getWeaponPower())
        p?.getWeaponPower()?.let {
            it.damage += magicNumber
        }
    }
}