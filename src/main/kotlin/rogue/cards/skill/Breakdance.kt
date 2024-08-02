package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.TradeCard
import rogue.cards.AbstractRogueCard
import rogue.mods.PersistMod
import utils.addMod

class Breakdance() :
    AbstractRogueCard(
        name = Breakdance::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {

    init {
        setMagicNumber(2)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsAction(
            AbstractDungeon.player.discardPile.group,
            1,
            TradeCard.tradeStrings.EXTENDED_DESCRIPTION[0],
            true,
            { true }) { cards ->
            cards.forEach {
                it.addMod(PersistMod(magicNumber, true))
                addToTop(
                    MoveCardsAction(
                        AbstractDungeon.player.hand,
                        AbstractDungeon.player.discardPile
                    ) { c: AbstractCard -> c == it })
            }
        })
    }
}