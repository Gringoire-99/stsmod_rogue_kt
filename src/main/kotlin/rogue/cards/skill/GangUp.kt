package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect
import rogue.cards.AbstractRogueCard

class GangUp() :
    AbstractRogueCard(
        name = GangUp::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
        CardModifierManager.addModifier(this, ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade { upgradeBaseCost(0) }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SelectCardsInHandAction(1, "选择一张卡消耗", false, false, { true }) { cards ->
            cards.forEach {
                val c = it
                repeat(magicNumber) {
                    AbstractDungeon.effectList.add(ShowCardAndAddToDiscardEffect(c.makeStatEquivalentCopy()))
                }
                addToBot(ExhaustSpecificCardAction(it, p?.hand))
            }
        })
    }
}