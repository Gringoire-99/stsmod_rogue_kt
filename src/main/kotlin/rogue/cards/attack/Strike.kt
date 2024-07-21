package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.dealDamage


class Strike :
    AbstractRogueCard(
        name = Strike::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.BASIC,
        target = CardTarget.ENEMY
    ) {

    init {
        setDamage(6)
        this.tags.apply {
            add(CardTags.STRIKE)
            add(CardTags.STARTER_STRIKE)
        }
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(3)
        }
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
    }
}