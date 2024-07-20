package rogue.power.weapon

import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import rogue.cards.AbstractWeaponPowerCard
import utils.isOtherClassCard

class SpectralCutlass(
    damage: Int = 3,
    duration: Int = 3,
    magic: Int = 2,
    upgraded: Boolean = false
) : AbstractWeaponPower(
    rawName = SpectralCutlass::class.simpleName.toString(),
    damage,
    duration, magic = magic, upgraded = upgraded
) {
    init {
        leechCount = magic
    }

    override fun onAfterUseCard(card: AbstractCard?, action: UseCardAction?) {
        if (card?.isOtherClassCard((owner as? AbstractPlayer)?.cardColor) == true) {
            duration++
            damage++
        }
    }

    override fun makeCopy(): AbstractWeaponPowerCard {
        val c = rogue.cards.power.SpectralCutlass()
        if (upgraded) c.upgrade()
        return c
    }

}