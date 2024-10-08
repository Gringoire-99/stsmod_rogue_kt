package rogue.cards.power

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.audio.CardAudioList
import rogue.cards.AbstractHeroCard
import rogue.power.hero.DeathsShadowPower
import utils.gainBlock

class ValeeraTheHollow :
    AbstractHeroCard(ValeeraTheHollow::class.simpleName.toString()) {
    init {
        tags.add(BaseModCardTags.FORM)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        CardAudioList.ValeeraTheHollowPlay.play()
        gainBlock(p, b = block)
        p?.apply {
            addToBot(ApplyHeroPowerAction(DeathsShadowPower(this)))
        }
    }
}