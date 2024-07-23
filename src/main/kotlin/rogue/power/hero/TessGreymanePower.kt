package rogue.power.hero

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import rogue.mods.ReduceCostMod
import rogue.power.common.StealthPower
import utils.addMod
import utils.discovery
import utils.gainBlock

class TessGreymanePower(owner: AbstractPlayer) :
    AbstractHeroPower(owner = owner, powerName = TessGreymanePower::class.simpleName.toString(), amount = 1) {

    private var useEnergy = 1
    override val ability: () -> Unit = {
        if (amount > 0) {
            val currentEnergy = EnergyPanel.getCurrentEnergy()
            if (currentEnergy - useEnergy >= 0) {
                EnergyPanel.useEnergy(useEnergy)
                flash()
                discovery {
                    specialAbility(it)
                    it.addMod(RetainMod())
                }
            }
            amount--
        }
    }
    val ability1 = { _: AbstractCard ->
        addToBot(ApplyPowerAction(owner, owner, StealthPower(owner)))
        gainBlock(owner, 4)
    }
    val ability2 = { c: AbstractCard ->
        c.addMod(ReduceCostMod(999))
    }
    val ability3 = {
        useEnergy = 0
        amount++
    }
    private val ability4 = { c: AbstractCard ->
        ability1(c)
        ability2(c)
    }
    var specialAbility: (AbstractCard) -> Unit = ability1
    private var specialAbilityCount = 1
        set(value) {
            field = value
            when (field) {
                1 -> specialAbility = ability1
                2 -> specialAbility = ability2
                3 -> {
                    specialAbility = {}
                    ability3()
                }

                4 -> {
                    ability3()
                    specialAbility = ability4
                    field = 0
                }
            }
            flash()
        }
    override val afterApply = {
        getBaseAbility()?.rightClick = ability
    }

    override fun atStartOfTurnPostDraw() {
        amount = 1
        useEnergy = 1
        specialAbilityCount++
    }

}