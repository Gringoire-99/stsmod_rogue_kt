package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.cards.AbstractRogueCard
import rogue.mods.NecriumMod
import utils.addMod

class NecriumVial :
    AbstractRogueCard(
        name = NecriumVial::class.simpleName.toString(),
        cost = 2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(4)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
            addMod(RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsAction(
            AbstractDungeon.player.discardPile.group,
            magicNumber,
            TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0],
            true,
            { true }) { cards ->
            cards.forEach {
                it.addMod(NecriumMod())
            }
        })
    }
}