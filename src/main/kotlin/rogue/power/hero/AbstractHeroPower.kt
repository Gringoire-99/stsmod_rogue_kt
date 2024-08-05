package rogue.power.hero

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.helpers.RelicLibrary
import rogue.power.IAbstractPower
import rogue.relics.HeroPower
import utils.makeId

abstract class AbstractHeroPower(owner: AbstractPlayer, powerName: String, stackAmount: Int = -1) :
    IAbstractPower(powerName = powerName, owner = owner, amount = stackAmount) {

    abstract val ability: () -> Unit
    fun getBaseAbility(): HeroPower? {
        (this.owner as? AbstractPlayer)?.apply {
            var relic = this.getRelic(HeroPower::class.makeId()) as HeroPower?
            if (relic == null) {
                relic = RelicLibrary.getRelic(HeroPower::class.makeId()) as HeroPower
                relic.isTemp = true
                relics.add(relic)
                reorganizeRelics()
            }
            return relic
        }
        return null
    }

    open val afterApply: () -> Unit = {}

    override fun onRemove() {
        getBaseAbility()?.reset()
    }
}