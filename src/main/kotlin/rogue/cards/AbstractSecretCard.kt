package rogue.cards

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster

abstract class AbstractSecretCard(val rawName: String, val powerId: String, rarity: CardRarity, cost: Int = 1) :
    AbstractRogueCard(
        name = rawName,
        cost = cost,
        type = CardType.POWER,
        rarity = rarity,
        target = CardTarget.SELF
    ) {
    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {

        if (p?.hasPower(powerId) == true) {
            return false
        }
        return super.canUse(p, m)
    }
}