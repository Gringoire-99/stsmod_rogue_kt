package rogue.cards.skill

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.DrawCardFromDiscardPileAction
import rogue.cards.AbstractComboCard
import rogue.mods.ReduceCostMod
import utils.addMod

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
                    card.addMod(ReduceCostMod(1, isTurnEffect = true))
                }
            })
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return isComboOn && p?.discardPile?.isEmpty == false
    }
}