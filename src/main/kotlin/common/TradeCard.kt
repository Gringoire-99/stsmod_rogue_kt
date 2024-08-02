package common

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.makeId

class TradeCard :
    AbstractRogueCard(
        TradeCard::class.simpleName.toString(), 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    companion object {
        val tradeStrings = CardCrawlGame.languagePack.getCardStrings(TradeCard::class.makeId())
    }

    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {
    }
}