package rogue.cards.skill

import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import rogue.cards.Mimicable
import utils.generateCardChoices
import utils.mimic

class Cheatsheet :
    AbstractRogueCard(
        name = Cheatsheet::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ), Mimicable {
    override var targetCard: AbstractCard? = null

    init {
        CardModifierManager.addModifier(this, RetainMod())
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        val t = generateCardChoices(CardFilter(), 1)[0]
        if (upgraded) {
            t.upgrade()
        }
        this.mimic(t)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return targetCard?.canUse(p, m) ?: false
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