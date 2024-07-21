package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.Mimicable
import utils.addMod
import utils.mimic

class ShadowReflection :
    AbstractRogueCard(
        name = ShadowReflection::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ), Mimicable {

    init {
        addMod(RetainMod(), ExhaustMod())
    }

    override var targetCard: AbstractCard? = null
    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return targetCard?.canUse(p, m) ?: false
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.apply {
            this@ShadowReflection.mimic(this@apply)
            this@ShadowReflection.exhaust = true
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        targetCard?.use(p, m)
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy()
        (copy as Mimicable).targetCard = targetCard
        return copy
    }
}