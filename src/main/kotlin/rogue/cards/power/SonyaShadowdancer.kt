package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.audio.AudioList
import rogue.cards.AbstractRogueCard
import rogue.power.common.ShadowdancePower

class SonyaShadowdancer() :
    AbstractRogueCard(
        name = SonyaShadowdancer::class.simpleName.toString(),
        cost = 1,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        AudioList.SonyaShadowdancerPlay.play()
        p?.apply {
            addToBot(ApplyPowerAction(p, p, ShadowdancePower(p, magicNumber), magicNumber))
        }
    }
}