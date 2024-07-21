package rogue.power.hero

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.helpers.RelicLibrary
import rogue.power.IAbstractPower
import rogue.relics.RogueBaseAbility
import utils.makeId

abstract class AbstractHeroPower(owner: AbstractPlayer, powerName: String) :
    IAbstractPower(powerName = powerName, owner = owner, amount = -1) {

    abstract val ability: () -> Unit
    fun getBaseAbility(): RogueBaseAbility? {
        (this.owner as? AbstractPlayer)?.apply {
            var relic = this.getRelic(RogueBaseAbility::class.makeId()) as RogueBaseAbility?
            if (relic == null) {
                relic = RelicLibrary.getRelic(RogueBaseAbility::class.makeId()) as RogueBaseAbility
                relic.isTemp = true
                relics.add(relic)
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