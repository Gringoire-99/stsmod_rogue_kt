package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractWeaponCard
import rogue.mods.TempCardMod
import utils.addMod
import utils.attackWithWeapon

class MirageBlade :
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
        addMod(TempCardMod())
        setMagicNumber(2)
    }

    companion object {
        val preview = MirageBlade()
    }

    override fun upgrade() {
        upgradeMagicNumber(1)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val d = damage
        repeat(magicNumber) {
            p?.attackWithWeapon(damage = d, target = m)
        }
    }
}