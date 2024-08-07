package rogue.cards.skill

import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.AbstractMimicCard
import rogue.mods.TempCardMod
import utils.addMod

class ShadowReflection :
    AbstractMimicCard(
        name = ShadowReflection::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ) {

    init {
        addMod(TempCardMod())
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.let {
            val copy = it.makeStatEquivalentCopy()
            if (upgraded && !copy.upgraded) {
                copy.upgrade()
            }
            this.mimic(copy)
        }
    }

}