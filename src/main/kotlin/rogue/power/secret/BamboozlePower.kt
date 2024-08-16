package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import utils.dealDamage
import utils.isAlive

class BamboozlePower(owner: AbstractPlayer, val magic: Int = 3) :
    AbstractSecretPower(rawName = BamboozlePower::class.simpleName.toString(), owner = owner), OnLoseBlockPower {
    private var last = 0
    var target: AbstractMonster? = null

    init {
        updateDescription()
    }

    override fun effect() {
        val last = last
        addToTop(EmptyAction {
            val m = AbstractDungeon.getMonsters().monsters.filter {
                it !== target && it.isAlive()
            }.randomOrNull()
            if (m == null && target != null) {
                dealDamage(
                    owner,
                    target,
                    magic,
                    DamageInfo(target, magic, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
            } else if (m != null) {
                dealDamage(
                    owner,
                    m,
                    last,
                    DamageInfo(m, last, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
            }
        })
    }

    override fun onLoseBlock(info: DamageInfo?, damage: Int): Int {
        if (info?.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.owner != owner) {
            if (damage <= owner.currentBlock && info.owner is AbstractMonster) {
                target = info.owner as? AbstractMonster
                last = damage
                triggerEffect()
            }
        }
        return damage
    }


    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magic)
        name = powerString.NAME
    }
}