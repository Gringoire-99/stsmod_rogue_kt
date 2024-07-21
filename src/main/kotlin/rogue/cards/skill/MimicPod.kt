package rogue.cards.skill

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.MimicAction
import rogue.cards.AbstractRogueCard
import rogue.cards.Mimicable

class MimicPod() :
    AbstractRogueCard(
        name = MimicPod::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.NONE
    ), Mimicable {

    override var targetCard: AbstractCard? = null
    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        addToBot(MimicAction(this))
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        utils.logger.info("$targetCard,${targetCard?.canUse(p, m)}")
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