package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import utils.addMod
import utils.attackWithWeapon
import utils.makeId

class Attack :
    AbstractWeaponCard(
        name = Attack::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.BASIC,
        target = CardTarget.ENEMY,
        loseDuration = 1,
        color = CardColor.COLORLESS

    ) {
    companion object {
        val id = Attack::class.makeId()
    }

    init {
        purgeOnUse = true
        addMod(ExhaustMod(), RetainMod())
    }

    override fun upgrade() {
        useUpgrade {
            loseDuration = 0
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.attackWithWeapon(damage = damage, target = m, loseDuration = loseDuration)
    }

}