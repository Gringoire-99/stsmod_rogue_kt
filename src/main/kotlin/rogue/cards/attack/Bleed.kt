package rogue.cards.attack

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.dealDamage
import utils.getRandomMonster

class Bleed() :
    AbstractRogueCard(
        name = Bleed::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.ENEMY,
        color = CardColor.COLORLESS
    ) {
    init {
        setDamage(2)
        AutoplayField.autoplay.set(this, true)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, getRandomMonster(), damage)
    }
}