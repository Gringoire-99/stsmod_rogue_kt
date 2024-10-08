package rogue.cards.power

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.action.StunAllMonsterAction
import rogue.audio.CardAudioList
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
        CardAudioList.ShadowcrafterScabbsPlay.play()
        gainBlock(p, b = block)
        p?.apply {
            addToBot(ApplyHeroPowerAction(SleightOfHandPower(this)))
            addToBot(StunAllMonsterAction(p))
        }

    }
}