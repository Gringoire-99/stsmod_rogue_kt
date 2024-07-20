package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import utils.addMod
import utils.attackWithWeapon

class MirageBlade() :
    AbstractWeaponCard(
        name = MirageBlade::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.ENEMY,
        color = CardColor.COLORLESS
    ) {
    init {
        purgeOnUse = true
        addMod(RetainMod())
        setMagicNumber(1)
    }

    override fun upgrade() {
        upgradeMagicNumber(1)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.attackWithWeapon(damage = damage, target = m)
    }
}