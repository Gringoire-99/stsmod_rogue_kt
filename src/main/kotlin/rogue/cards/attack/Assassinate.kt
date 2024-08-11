package rogue.cards.attack

import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.ClashEffect
import rogue.cards.AbstractWeaponCard
import utils.attackWithWeapon
import utils.isAttackIntent

class Assassinate : AbstractWeaponCard(
    name = Assassinate::class.simpleName.toString(),
    cost = 1,
    type = CardType.ATTACK,
    rarity = CardRarity.COMMON,
    target = CardTarget.ENEMY
) {
    init {
        setDamage(3)
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(3)
        }
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val d = damage
        val count = if (m != null && !m.intent.isAttackIntent()) magicNumber + 1 else 1
        repeat(count) {
            p?.attackWithWeapon(damage = d, target = m) {
                addToBot(VFXAction(ClashEffect(it.hb.cX, it.hb.cY), 0.1f))
            }
        }


    }
}