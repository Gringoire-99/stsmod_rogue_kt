package rogue.cards.attack

import basemod.cardmods.EtherealMod
import basemod.cardmods.ExhaustMod
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
        loseDurability = 1,
        color = CardColor.COLORLESS

    ) {
    companion object {
        val id = Attack::class.makeId()
    }

    init {
        addMod(ExhaustMod(), EtherealMod())
    }

    override fun upgrade() {
        useUpgrade {
            loseDurability = 0
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.attackWithWeapon(damage = damage, target = m, loseDurability = loseDurability)
    }

}