package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.cards.AbstractHeroCard
import rogue.power.hero.TessGreymanePower
import utils.gainBlock

class TessGreymane() :
    AbstractHeroCard(TessGreymane::class.simpleName.toString()) {


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, b = block)
        p?.apply {
            addToBot(ApplyHeroPowerAction(this, TessGreymanePower(this)))
        }
    }
}