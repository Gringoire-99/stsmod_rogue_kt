package rogue.cards.attack

import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import rogue.action.XCardAction
import rogue.cards.AbstractWeaponCard
import rogue.power.common.StealthPower
import utils.attackWithWeapon
import utils.makeId

class Sabotage :
    AbstractWeaponCard(
        name = Sabotage::class.simpleName.toString(),
        cost = -1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setMagicNumber(1)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val d = damage
        val count = if (upgraded) EnergyPanel.getCurrentEnergy() + 1 else EnergyPanel.getCurrentEnergy()
        addToBot(XCardAction(count = count, effect = {
            p?.attackWithWeapon(m, d)
        }, cb = {
            if (p?.hasPower(StealthPower::class.makeId()) == true) {
                addToBot(GainEnergyAction(magicNumber))
            }
        }))
    }
}