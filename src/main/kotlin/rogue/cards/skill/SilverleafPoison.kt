package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower
import utils.isWeaponEquipped

class SilverleafPoison() :
    AbstractRogueCard(
        name = SilverleafPoison::class.simpleName.toString(),
        cost = 0,
        rarity = CardRarity.UNCOMMON, type = CardType.SKILL,
        target = CardTarget.SELF
    ) {


    init {
        setMagicNumber(1)
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            CardModifierManager.addModifier(this, RetainMod())
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.getWeaponPower()?.apply {
            drawCount += magicNumber
        }
    }
}