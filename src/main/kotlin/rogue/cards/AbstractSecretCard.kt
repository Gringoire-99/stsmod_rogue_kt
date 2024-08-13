package rogue.cards

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster

abstract class AbstractSecretCard(val rawName: String, rarity: CardRarity, cost: Int = 1) : AbstractRogueCard(
    name = rawName,
    cost = cost,
    type = CardType.POWER,
    rarity = rarity,
    target = CardTarget.SELF
) {
    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            if (hasPower(cardID)) {
                return false
            }
        }
        return canUse(p, m)
    }
}