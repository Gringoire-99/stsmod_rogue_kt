package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.mods.NecriumMod
import rogue.mods.SpreadNecriumMod
import utils.addMod

class MyraRotspring() :
    AbstractRogueCard(
        name = MyraRotspring::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            addMod(RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(1, "选择一张卡", false, false, { true }) { cards ->
            cards.forEach {
                it.addMod(NecriumMod(), SpreadNecriumMod())
            }
        })
    }
}