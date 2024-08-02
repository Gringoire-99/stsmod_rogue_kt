package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.attack.Razorpetal

class RazorpetalVolley :
    AbstractRogueCard(
        name = RazorpetalVolley::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
        CardModifierManager.addModifier(this, ExhaustMod())
        cardsToPreview = Razorpetal.preview
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(MakeTempCardInHandAction(Razorpetal(), magicNumber))
    }
}