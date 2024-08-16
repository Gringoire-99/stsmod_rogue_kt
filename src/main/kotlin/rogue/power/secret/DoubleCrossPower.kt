package rogue.power.secret

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.action.EmptyAction
import utils.drawCard
import utils.isAlive


class DoubleCrossPower(owner: AbstractPlayer, val magicNumber: Int = 1) :
    AbstractSecretPower(rawName = DoubleCrossPower::class.simpleName.toString(), owner = owner) {

    init {
        updateDescription()
    }

    override fun atStartOfTurnPostDraw() {
        val target = AbstractDungeon.getMonsters().monsters.filter { it.isAlive() }
        if (target.size == 1) {
            triggerEffect()
        }
    }

    override fun effect() {
        addToTop(EmptyAction {
            if (AbstractDungeon.getMonsters().monsters.filter { it.isAlive() }.size == 1) {
                drawCard(magicNumber)
                flash()
            }
        })
    }

    override fun updateDescription() {
        description = powerString.DESCRIPTIONS[0].format(magicNumber)
        name = powerString.NAME
    }
}