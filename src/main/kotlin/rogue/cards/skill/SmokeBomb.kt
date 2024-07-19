package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.power.StealthPower
import utils.addMod
import utils.gainBlock

class SmokeBomb() :
    AbstractRogueCard(
        name = SmokeBomb::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.ALL
    ) {
    init {
        setBlock(4)
        addMod(RetainMod(), ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade { upgradeBlock(2) }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(1, "选择一张卡丢弃", false, false, { true }) { cards ->
            cards.forEach {
                addToBot(DiscardSpecificCardAction(it))
            }
        })
        gainBlock(p, block)
        addToBot(ApplyPowerAction(p, p, StealthPower(p ?: AbstractDungeon.player), 1))
        AbstractDungeon.getMonsters().monsters.forEach {
            addToBot(ApplyPowerAction(it, p, StealthPower(it), 1))
        }
    }
}