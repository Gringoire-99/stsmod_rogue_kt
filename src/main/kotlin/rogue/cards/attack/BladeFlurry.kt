package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractWeaponCard
import utils.attackWithWeapon
import utils.getWeaponPower
import kotlin.math.min

class BladeFlurry :
    AbstractWeaponCard(
        name = BladeFlurry::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ALL_ENEMY,
    ) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            getWeaponPower()?.apply {
                val count: Int = min(magicNumber, durability)
                repeat(count) {
                    val d = this@BladeFlurry.damage
                    addToBot(EmptyAction {
                        p.attackWithWeapon(damage = d, loseDurability = 1)
                    })
                }
            }
        }

    }
}