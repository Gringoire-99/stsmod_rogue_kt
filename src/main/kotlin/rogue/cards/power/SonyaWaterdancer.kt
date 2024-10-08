package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.audio.CardAudioList
import rogue.cards.AbstractRogueCard
import rogue.power.common.WaterdancePower

class SonyaWaterdancer() :
    AbstractRogueCard(
        name = SonyaWaterdancer::class.simpleName.toString(),
        cost = 3,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {

    init {

        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        CardAudioList.SonyaWaterdancerPlay.play()
        p?.apply {
            addToBot(ApplyPowerAction(p, p, WaterdancePower(p, magicNumber), 1))
        }
    }
}