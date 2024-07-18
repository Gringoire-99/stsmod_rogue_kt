package rogue.relics

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import rogue.action.EquipWeaponAction
import rogue.power.weapon.WickedKnife

class RogueBaseAbility() :
    IAbstractRelic(
        name = RogueBaseAbility::class.simpleName.toString(),
        tier = RelicTier.STARTER,
        sfx = LandingSound.CLINK
    ), ClickableRelic {
    //        消耗一费，装备邪恶短刀
    override fun onRightClick() {
        val currentEnergy = EnergyPanel.getCurrentEnergy()
        if (currentEnergy > 0) {
            EnergyPanel.useEnergy(1)
            addToBot(EquipWeaponAction(WickedKnife()))
        }
    }


}