package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.mods.AfterUsesMod
import rogue.mods.TurnEffectMod
import utils.addMod

class Preparation :
    AbstractRogueCard(
        name = Preparation::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(2)
        addMod(ExhaustMod(), RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(magicNumber, "选择保留的卡", { card ->
            card != this
        }, { cards ->
            cards.forEach { card ->
                card.retain = true
                CardModifierManager.addModifier(card, TurnEffectMod(1) { c ->
                    c.costForTurn = if (cost - 1 < 0) 0 else c.cost - 1
                    CardModifierManager.addModifier(c, AfterUsesMod(1) {
                        c.costForTurn = c.cost
                    })
                })
            }

        }))
    }
}