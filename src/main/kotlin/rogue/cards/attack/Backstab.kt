package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import utils.*

class Backstab :
    AbstractRogueCard(
        name = Backstab::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(4)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val d = damage
        val wd = p?.getWeaponPower()?.damage ?: 0
        if (m != null && p?.isWeaponEquipped() == true && useSneakAttack(m) {
                p.attackWithWeapon(damage = d + wd, target = m)
            }) else {
            dealDamage(p, m, damage)
        }
    }
}