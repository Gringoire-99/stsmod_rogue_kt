package rogue.power.secret

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.power.weapon.AbstractWeaponPower
import utils.canSneakAttack
import utils.isAlive
import utils.useSneakAttack


class AmbushPower(owner: AbstractPlayer, val magic: Int = 1) :
    AbstractSecretPower(rawName = AmbushPower::class.simpleName.toString(), owner = owner) {
    init {
        updateDescription()
    }

    override fun effect() {
        val player = AbstractDungeon.player
        val power = player.getPower(AbstractWeaponPower.id) as AbstractWeaponPower?
        if (power != null) {
            val monsters = AbstractDungeon.getMonsters().monsters
            monsters.forEach { m ->
                repeat(magic) {
                    if (m.isAlive()) {
                        useSneakAttack(m) {
                            power.attack(target = m, damage = power.damage)
                            this@AmbushPower.flash()
                        }
                    }
                }
            }
        }
    }


    override fun atEndOfRound() {
        val player = AbstractDungeon.player
        val power = player.getPower(AbstractWeaponPower.id) as AbstractWeaponPower?
        val targets = AbstractDungeon.getMonsters().monsters.filter { it.isAlive() && canSneakAttack(it) }
        if (power != null && targets.isNotEmpty()) {
            triggerEffect()
        }
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magic)
        name = powerString.NAME
    }

}