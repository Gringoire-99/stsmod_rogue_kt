package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower
import utils.isWeaponEquipped
import utils.makeId

class SilverleafPoison :
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

    companion object {
        val name = CardCrawlGame.languagePack.getCardStrings(SilverleafPoison::class.makeId()).NAME
    }
    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            p?.getWeaponPower()?.apply {
                drawCount += magicNumber
            }
        })
    }
}