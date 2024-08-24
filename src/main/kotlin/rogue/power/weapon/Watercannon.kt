package rogue.power.weapon

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractWeaponPowerCard
import utils.addToQueue

class Watercannon(
    damage: Int = 2,
    durability: Int = 2,
    magicNumber: Int = 3
) : AbstractWeaponPower(
    rawName = Watercannon::class.simpleName.toString(),
    damage = damage,
    durability = durability,
    magic = magicNumber
) {
    private var usage = magic
    override fun atStartOfTurnPostDraw() {
        usage = magic
    }

    init {
        damageModifier.cbOfOnAttack.add { _: DamageInfo?, _: Int, c: AbstractCreature? ->
            if (usage > 0) {
                usage--
                addToTop(EmptyAction {
                    val randomOrNull =
                        AbstractDungeon.player.drawPile.group.filter { (it.costForTurn == 0 || it.freeToPlayOnce) && it.type == AbstractCard.CardType.ATTACK }
                            .randomOrNull()
                    if (randomOrNull != null && c is AbstractMonster) {
                        addToQueue(randomOrNull, c, purge = true)
                    }
                })
            }

        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.Watercannon()
        if (upgraded) c.upgrade()
        return c
    }
}