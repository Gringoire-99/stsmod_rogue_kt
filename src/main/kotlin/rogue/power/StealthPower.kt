package rogue.power

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower
import com.megacrit.cardcrawl.actions.common.EndTurnAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.DexterityPower

class StealthPower(owner: AbstractCreature) :
    IAbstractPower(powerName = StealthPower::class.simpleName.toString(), owner = owner), OnLoseBlockPower {
    init {
        var gainBlock = 2
        val dexterity: AbstractPower? = owner.getPower(DexterityPower.POWER_ID)
        gainBlock += dexterity?.amount ?: 0
        this.addToBot(GainBlockAction(owner, gainBlock))
        flash()
        updateDescription()
    }


    //        移除自身
    override fun atStartOfTurn() {
        flash()
        AbstractDungeon.actionManager.addToBottom(RemoveSpecificPowerAction(owner, owner, this))
    }

    override fun onLoseBlock(info: DamageInfo?, lose: Int): Int {
        //  如果伤害小于格挡则变为0
        // 伤害必须为normal,自伤不触发
        if (info == null || info.type != DamageInfo.DamageType.NORMAL || info.owner == owner || lose <= 0) {
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