package rogue.cards.skill

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.CardFilter
import rogue.cards.AbstractRogueCard
import rogue.cards.Tradeable
import utils.discovery

class Parrrley(override var isEnableTrade: Boolean = true) :
    AbstractRogueCard(
        name = Parrrley::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ), Tradeable {
    init {
        ExhaustiveVariable.setBaseValue(this, 2)
    }

    var lastDiscoveredCard: AbstractCard? = null
    override fun upgrade() {
        useUpgrade {
            ExhaustiveVariable.upgrade(this, 1)
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val cardFilter = CardFilter(cardType = hashSetOf(CardType.SKILL, CardType.ATTACK))
        discovery(cardFilter) {
            lastDiscoveredCard = it.makeSameInstanceOf()
            ExhaustiveVariable.setBaseValue(it, 1)
            CardModifierManager.addModifier(it, EtherealMod())
            CardModifierManager.addModifier(it, ExhaustMod())
            it.freeToPlayOnce = true
        }
    }

    override fun onTrade() {
        if (lastDiscoveredCard != null) {
            addToBot(AddCardToDeckAction(lastDiscoveredCard))
            AbstractDungeon.player.masterDeck.removeCard(this.cardID)
            addToBot(ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile))
        }
    }
}