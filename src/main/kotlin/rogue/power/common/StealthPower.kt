package rogue.power.common

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.DexterityPower
import rogue.power.IAbstractPower
import rogue.power.hero.DeathsShadowPower
import utils.makeId
import kotlin.math.max

class StealthPower(owner: AbstractCreature, stackAmount: Int = 1) :
    IAbstractPower(powerName = StealthPower::class.simpleName.toString(), owner = owner, amount = stackAmount),
    OnLoseBlockPower {
    init {
        var gainBlock = 2
        val dexterity: AbstractPower? = owner.getPower(DexterityPower.POWER_ID)
        gainBlock += max(0, dexterity?.amount ?: 0) * stackAmount
        this.addToBot(GainBlockAction(owner, gainBlock))
        flash()
    }


    //        移除自身
    override fun atStartOfTurn() {
        flash()
        if (owner.hasPower(DeathsShadowPower::class.makeId())) {
            owner?.getPower(StealthPower::class.makeId())?.amount = 1
        } else {
            AbstractDungeon.actionManager.addToBottom(RemoveSpecificPowerAction(owner, owner, this))

        }
    }

    override fun onLoseBlock(info: DamageInfo?, lose: Int): Int {
        //  如果伤害小于格挡则变为0
        // 伤害必须为normal,自伤不触发
        val hasDeathsShadowPower = owner.hasPower(DeathsShadowPower::class.makeId())
        if (info == null || (!hasDeathsShadowPower && (info.type != DamageInfo.DamageType.NORMAL || info.owner == owner)) || lose <= 0) {
            return lose
        }
        val currentBlock = owner.currentBlock
        if (currentBlock >= lose) {
            this.flash()
            return 0
        }
        return lose
    }


}