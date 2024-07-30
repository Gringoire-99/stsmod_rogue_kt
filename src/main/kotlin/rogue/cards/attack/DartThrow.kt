package rogue.cards.attack

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect
import rogue.action.EmptyAction
import rogue.cards.AbstractComboCard
import rogue.cards.skill.TheCoin
import utils.dealDamage
import utils.getRandomMonster

class DartThrow() :
    AbstractComboCard(
        name = DartThrow::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ALL_ENEMY
    ) {
    init {
        setMagicNumber(2)
        setDamage(3)
        cardsToPreview = TheCoin()
        ExhaustiveVariable.setBaseValue(this, 2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        var times = magicNumber
        useCombo {
            times += 1
        }
        val count = hashMapOf<AbstractMonster, Int>()
        repeat(times) {
            addToBot(EmptyAction {
                val m1 = getRandomMonster()
                m1?.let {
                    count[m1] = count.getOrPut(m1) { 0 } + 1
                    dealDamage(p, m1, damage, damageEffect = AbstractGameAction.AttackEffect.BLUNT_LIGHT) {
                        addToBot(VFXAction(ThrowDaggerEffect(it.hb.cX, it.hb.cY)));
                    }
                }
            })
        }
        addToBot(EmptyAction {
            val sum = count.values.sumOf { it - 1 }
            if (sum > 0) {
                addToBot(MakeTempCardInHandAction(TheCoin(), sum))
            }
        })
    }
}