package rogue.cards.attack

import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.vfx.combat.ClashEffect
import rogue.cards.AbstractWeaponCard
import utils.attackWithWeapon

class Assassinate : AbstractWeaponCard(
    name = Assassinate::class.simpleName.toString(),
    cost = 1,
    type = CardType.ATTACK,
    rarity = CardRarity.COMMON,
    target = CardTarget.ENEMY
) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade { upgradeMagicNumber(6) }
    }

    override fun modifyTempBaseDamage(baseDamage: IntArray) {
        super.modifyTempBaseDamage(baseDamage)
        AbstractDungeon.player?.getPower(DexterityPower.POWER_ID)?.apply {
            baseDamage[0] += magicNumber * amount
        }
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.attackWithWeapon(damage = damage, target = m) {
            addToBot(VFXAction(ClashEffect(it.hb.cX, it.hb.cY), 0.1f))
        }
    }
}