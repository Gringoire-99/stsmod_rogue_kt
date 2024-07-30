package rogue.cards.power

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.cards.AbstractHeroCard
import rogue.power.hero.TessGreymanePower
import utils.gainBlock
import utils.isOtherClassCard
import kotlin.math.max

class TessGreymane :
    AbstractHeroCard(TessGreymane::class.simpleName.toString()) {
    init {
        tags.add(BaseModCardTags.FORM)
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.let {
            if (it.isOtherClassCard()) {
                this.costForTurn = max(0, this.costForTurn - 1)
                isCostModified = true
            }
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, b = block)
        p?.apply {
            addToBot(ApplyHeroPowerAction(this, TessGreymanePower(this)))
        }
    }
}