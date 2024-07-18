package common

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard

class TradeCard :
    AbstractRogueCard(
        TradeCard::class.simpleName.toString(), 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF,
        color = CardColor.COLORLESS
    ) {
    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {
    }
}