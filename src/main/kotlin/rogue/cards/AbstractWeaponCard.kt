package rogue.cards

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.characters.RogueEnum
import utils.getWeaponPower
import utils.isWeaponEquipped

/**
 * 需要武器才能打出的一类卡,例如剑刃乱舞，伤害会被附加武器伤害
 */
abstract class AbstractWeaponCard(
    name: String,
    cost: Int,
    type: CardType,
    rarity: CardRarity,
    target: CardTarget,
    var loseDuration: Int = 0,
    color: CardColor = RogueEnum.HS_ROGUE_CARD_COLOR
) :
    AbstractRogueCard(
        name, cost, type, rarity, target, color = color
    ), OnCalculateCardDamage {

    init {
        setDamage(0)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }


    override fun modifyTempBaseDamage(baseDamage: IntArray) {
        AbstractDungeon.player?.apply {
            val weaponPower = getWeaponPower()
            weaponPower?.apply {
                baseDamage[0] = baseDamage[0] + weaponPower.damage
            }
        }
    }

    override fun modifyTempBaseDamageMulti(baseDamages: FloatArray) {
        AbstractDungeon.player?.apply {
            val weaponPower = getWeaponPower()
            weaponPower?.apply {
                baseDamages.forEachIndexed { index, d ->
                    baseDamages[index] = d + weaponPower.damage
                }
            }
        }
    }

}
