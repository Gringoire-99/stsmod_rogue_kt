package rogue.cards

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.power.weapon.AbstractWeaponPower
import utils.getWeaponPower
import utils.isWeaponEquipped

/**
 * 需要武器才能打出的一类卡,例如致命膏药，剑刃乱舞
 */
abstract class AbstractWeaponCard(
    name: String,
    cost: Int,
    type: CardType,
    rarity: CardRarity,
    target: CardTarget,
    var loseDuration: Int = 0
) :
    AbstractRogueCard(
        name, cost, type, rarity, target
    ) {
    init {
        AbstractWeaponPower.listOfWeaponCard.add(this)
        initDamage()
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    fun updateDamage(d: Int) {
        baseDamage = d
    }


    override fun triggerWhenDrawn() {
        initDamage()
    }

    override fun triggerWhenCopied() {
        initDamage()
    }
    /**
     * 绑定武器的伤害
     * （不知道是什么原因DamageModifier修改基础伤害的函数会不断执行，故弃用）
     *  TODO 尝试使用patch修改
     */
    private fun initDamage() {
        val weaponPower = AbstractDungeon.player?.getWeaponPower()
        baseDamage = weaponPower?.damage ?: 0
        utils.logger.info("初始化 武器牌攻击 $baseDamage $weaponPower")
    }

}
