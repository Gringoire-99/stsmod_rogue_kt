package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect
import rogue.action.EmptyAction
import rogue.cards.AbstractWeaponCard
import rogue.mods.ReduceCostMod
import rogue.power.weapon.AbstractWeaponPower
import utils.addMod
import utils.getWeaponPower

class Doomerang :
    AbstractWeaponCard(
        name = Doomerang::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ALL_ENEMY
    ) {
    init {
        addMod(RetainMod())
        setMagicNumber(2)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val d = damage
        p?.getWeaponPower()?.apply {
            repeat(magicNumber) {
                addToBot(SFXAction("ATTACK_HEAVY"))
                addToBot(VFXAction(p, CleaveEffect(), 0.1f))
                AbstractDungeon.getMonsters().monsters.forEach {
                    attack(target = it, damage = d)
                }
            }
            addToBot(EmptyAction {
                p.powers.removeIf {
                    it.ID == AbstractWeaponPower.id
                }
                val copy = makeCopy()
                if (this@Doomerang.upgraded) {
                    addToBot(EmptyAction {
                        copy.addMod(ReduceCostMod(1))
                    })
                }
                p.hand.addToHand(copy)
            })

        }
    }
}