package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.addMod
import utils.mimic

class ShadowReflection() :
    AbstractRogueCard(
        name = ShadowReflection::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ) {

    init {
        addMod(RetainMod(), ExhaustMod())
    }

    var targetCard: AbstractCard? = null
    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        val target = targetCard
        return target?.canUse(p, m) ?: false
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.apply {
            this@ShadowReflection.mimic(this@apply)
            this@ShadowReflection.exhaust = true
            this@ShadowReflection.targetCard = c
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        targetCard?.use(p, m)
    }
}