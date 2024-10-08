package rogue.power.common

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect
import rogue.power.IAbstractPower


class RenosLuckyHat(owner: AbstractCreature, private val copy: rogue.cards.power.RenosLuckyHat) :
    IAbstractPower(powerName = RenosLuckyHat::class.simpleName.toString(), owner = owner), NonStackablePower {
    init {
        this.flash()
        addToBot(ApplyPowerAction(owner, owner, StrengthPower(owner, copy.magicNumber), copy.magicNumber))
        addToBot(ApplyPowerAction(owner, owner, DexterityPower(owner, copy.magicNumber), copy.magicNumber))
    }

    // 被攻击时
    override fun onAttacked(info: DamageInfo, damageAmount: Int): Int {
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != null && info.owner !== this.owner && damageAmount > 0) {
            this.flash()
            addToTop(ApplyPowerAction(owner, owner, StrengthPower(owner, -copy.magicNumber), -copy.magicNumber))
            addToTop(ApplyPowerAction(owner, owner, DexterityPower(owner, -copy.magicNumber), -copy.magicNumber))
            AbstractDungeon.effectList.add(ShowCardAndAddToDiscardEffect(copy))
            addToTop(RemoveSpecificPowerAction(owner, info.owner, this))
        }
        return damageAmount
    }

}