package common

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard

class ScavengeCard(val opt: Int = 0) :
    AbstractRogueCard(
        name = ScavengeCard::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    init {
        val cardString: CardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID)
        rawDescription = cardString.EXTENDED_DESCRIPTION[opt]
        initializeDescription()
    }

    override fun upgrade() {
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
    }
}