package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import utils.attackWithWeapon
import utils.makeId

class Attack(baseD: Int = 0) :
    AbstractWeaponCard(
        name = Attack::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.BASIC,
        target = CardTarget.ENEMY,
        loseDuration = 1
    ) {
    companion object {
        val id = Attack::class.makeId()
    }

    init {
        purgeOnUse = true
        PurgeField.purge.set(this, true)
        setDamage(baseD)
        CardModifierManager.addModifier(this, ExhaustMod())
        CardModifierManager.addModifier(this, RetainMod())
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