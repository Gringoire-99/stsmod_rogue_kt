package rogue.cards.skill

import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.DrawCardFromDiscardPileAction
import rogue.cards.AbstractComboCard
import rogue.mods.AfterUsesMod

class Shadowstep :
    AbstractComboCard(
        name = Shadowstep::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {

        useCombo {
            addToBot(DrawCardFromDiscardPileAction { card ->
                if (this.upgraded) {
                    card.apply {
                        costForTurn = if (cost - 1 < 0) 0 else cost - 1
                    }
                    CardModifierManager.addModifier(card, AfterUsesMod(1) {
                        costForTurn = cost
                    })
                }
            })

        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return isComboOn && p?.discardPile?.isEmpty == false
    }
}