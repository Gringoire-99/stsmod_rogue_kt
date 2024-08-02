package rogue.relics

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import rogue.action.EquipWeaponAction
import rogue.power.weapon.WickedKnife

//        消耗一费，装备邪恶短刀
val baseRightClick = {
    val currentEnergy = EnergyPanel.getCurrentEnergy()
    if (currentEnergy > 0) {
        EnergyPanel.useEnergy(1)
        AbstractDungeon.actionManager.addToBottom(EquipWeaponAction(WickedKnife()))
    }
}
val baseStartOfTurn = {}

class RogueBaseAbility(
    var rightClick: () -> Unit = baseRightClick,
    var startOfTurn: () -> Unit = baseStartOfTurn,
    var isTemp: Boolean = false
) :
    IAbstractRelic(
        name = RogueBaseAbility::class.simpleName.toString(),
        tier = RelicTier.STARTER,
        sfx = LandingSound.CLINK
    ), ClickableRelic {
    override fun atTurnStartPostDraw() {
        startOfTurn()
    }

    override fun onRightClick() {
        rightClick()
    }

    override fun atBattleStart() {
        reset()
    }

    fun reset() {
        rightClick = baseRightClick
        startOfTurn = baseStartOfTurn
    }

    override fun onVictory() {
        if (isTemp) {
            AbstractDungeon.player.relics.remove(this)
            AbstractDungeon.player.reorganizeRelics()
        }
        reset()
    }
}