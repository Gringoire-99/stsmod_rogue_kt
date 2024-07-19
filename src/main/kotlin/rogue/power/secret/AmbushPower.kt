package rogue.power.secret

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.weapon.AbstractWeaponPower
import utils.isAttackIntent


class AmbushPower(owner: AbstractPlayer) :
    AbstractSecretPower(rawName = AmbushPower::class.simpleName.toString(), owner = owner), NonStackablePower {


    override fun atEndOfRound() {
        val player = AbstractDungeon.player
        val power = player.getPower(AbstractWeaponPower.id) as AbstractWeaponPower?
        if (power != null) {
            val monsters = AbstractDungeon.getMonsters().monsters
            monsters.forEach {
                if (!it.intent.isAttackIntent()) {
                    power.attack(target = it, damage = power.damage)
                }
            }
        }

    }
}