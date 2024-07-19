package rogue.power

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import rogue.action.AddCardInDiscardPile


class RenosLuckyHat(owner: AbstractCreature, val copy: rogue.cards.power.RenosLuckyHat) :
    IAbstractPower(powerName = RenosLuckyHat::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        this.flash()
        addToBot(ApplyPowerAction(owner, owner, StrengthPower(owner, copy.magicNumber), copy.magicNumber))
        addToBot(ApplyPowerAction(owner, owner, DexterityPower(owner, copy.magicNumber), copy.magicNumber))
    }

    // 被攻击时
    override fun onAttacked(info: DamageInfo, damageAmount: Int): Int {
        // 非荆棘伤害，非生命流失伤害，伤害来源不为空，伤害来源不是能力持有者本身，伤害大于0
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != null && info.owner !== this.owner && damageAmount > 0) {
            // 能力闪烁一下
            this.flash()
            addToTop(ApplyPowerAction(owner, owner, StrengthPower(owner, -copy.magicNumber), -copy.magicNumber))
            addToTop(ApplyPowerAction(owner, owner, DexterityPower(owner, -copy.magicNumber), -copy.magicNumber))
            addToTop(AddCardInDiscardPile(copy))
            addToTop(RemoveSpecificPowerAction(owner, info.owner, this))
        }
        return damageAmount
    }

}