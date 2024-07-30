package rogue.cards.skill

import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.action.MimicPodAction
import rogue.cards.AbstractMimicCard

class MimicPod :
    AbstractMimicCard(
        name = MimicPod::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.NONE
    ) {

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        addToBot(MimicPodAction(this))
    }
}