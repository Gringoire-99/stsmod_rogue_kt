package rogue.power.quest

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.power.common.AncientBlades
import utils.isOtherClassCard

class BazaarBurglaryPower(owner: AbstractPlayer) :
    AbstractQuestPower(rawName = BazaarBurglaryPower::class.simpleName.toString(), owner = owner) {
    override val maxCount: Int = 6
    override fun onComplete() {
        (owner as? AbstractPlayer)?.apply {
            addToBot(ApplyPowerAction(this@apply, this@apply, AncientBlades(this@apply)))
        }
    }

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        usedCard?.apply {
            if (this.isOtherClassCard()) {
                questCount++
            }
        }
    }
}