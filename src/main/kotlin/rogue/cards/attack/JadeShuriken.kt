package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect
import rogue.action.JadeShurikenAction
import rogue.cards.AbstractComboCard
import rogue.cards.OnCalculateCardDamage
import rogue.power.common.JadeGrowth
import utils.addMod
import utils.dealDamage
import utils.makeId

class JadeShuriken :
    AbstractComboCard(
        name = JadeShuriken::class.simpleName.toString(),
        cost = 2,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ), OnCalculateCardDamage {

    init {
        setMagicNumber(2)
        setDamage(11)
        addMod(RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(3)
            upgradeMagicNumber(1)
        }
    }

    override fun modifyTempBaseDamage(baseDamage: IntArray) {
        AbstractDungeon.player?.apply {
            val power = getPower(JadeGrowth::class.makeId())
            power?.apply {
                baseDamage[0] = baseDamage[0] + power.amount
            }
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {

        dealDamage(p, m, damage) {
            addToBot(VFXAction(ThrowDaggerEffect(it.hb.cX, it.hb.cY)));
        }
        addToTop(ApplyPowerAction(p, p, JadeGrowth(p ?: AbstractDungeon.player, magicNumber), magicNumber))
        p?.apply {
            m?.apply {
                useCombo {
                    addToBot(JadeShurikenAction(p, m, this@JadeShuriken))
                }
            }
        }

    }
}