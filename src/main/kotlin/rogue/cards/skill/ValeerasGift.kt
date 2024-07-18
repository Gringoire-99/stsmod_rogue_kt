package rogue.cards.skill

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.attack.Backstab
import rogue.cards.attack.FanOfKnives

class ValeerasGift() :
    AbstractRogueCard(
        name = ValeerasGift::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        CardModifierManager.addModifier(this, RetainMod())
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(
            SelectCardsAction(
                arrayListOf(Backstab(), DeadlyPoison(), FanOfKnives()),
                1,
                "选择一项",
                false,
                { true },
            ) { cards ->
                val target = cards[0]
                CardModifierManager.addModifier(target, EtherealMod())
                p?.apply {
                    hand.addToHand(target)
                }
            }
        )
    }
}