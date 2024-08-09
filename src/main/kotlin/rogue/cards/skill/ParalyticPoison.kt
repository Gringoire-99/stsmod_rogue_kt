package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower
import utils.makeId

class ParalyticPoison :
    AbstractRogueCard(
        name = ParalyticPoison::class.simpleName.toString(),
        cost = 0,
        rarity = CardRarity.UNCOMMON,
        type = CardType.SKILL,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    companion object {
        val name = CardCrawlGame.languagePack.getCardStrings(ParalyticPoison::class.makeId()).NAME
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
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

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            p?.getWeaponPower()?.apply {
                paralysisCount += magicNumber
            }
        })
    }
}