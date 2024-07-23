package rogue.cards.power

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.action.StunAllMonsterAction
import rogue.cards.AbstractHeroCard
import rogue.power.hero.SleightOfHandPower
import utils.gainBlock

class ShadowcrafterScabbs() :
    AbstractHeroCard(
        rawName = ShadowcrafterScabbs::class.simpleName.toString(),
    ) {
    init {
        tags.add(BaseModCardTags.FORM)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, b = block)
        p?.apply {
            addToBot(ApplyHeroPowerAction(this, SleightOfHandPower(this)))
            addToBot(StunAllMonsterAction(p))
        }

    }
}