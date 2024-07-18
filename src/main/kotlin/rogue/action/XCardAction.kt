package rogue.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.ChemicalX
import com.megacrit.cardcrawl.ui.panels.EnergyPanel

class XCardAction(
    private val owner: AbstractPlayer? = AbstractDungeon.player,
    private var count: Int = EnergyPanel.getCurrentEnergy(),
    val effect: () -> Unit,
    val cb: () -> Unit
) :
    AbstractGameAction() {
    override fun update() {
        if (owner?.hasPower(ChemicalX.ID) == true) {
            count += 2
        }
        repeat(count) {
            effect()
        }
        EnergyPanel.useEnergy(EnergyPanel.getCurrentEnergy())
        cb()
        isDone = true
    }

}