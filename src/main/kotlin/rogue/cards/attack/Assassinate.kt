package rogue.cards.attack

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DexterityPower
import rogue.cards.AbstractWeaponCard
import utils.attackWithWeapon

class Assassinate() : AbstractWeaponCard(
    name = Assassinate::class.simpleName.toString(),
    cost = 1,
    type = CardType.ATTACK,
    rarity = CardRarity.COMMON,
    target = CardTarget.ENEMY
) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade { upgradeMagicNumber(2) }
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        val dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID)
        if (dexterity != null) {
            baseDamage += dexterity.amount * magicNumber
        }
        super.calculateCardDamage(mo)
        if (dexterity != null) {
            baseDamage -= dexterity.amount * magicNumber
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.attackWithWeapon(damage = damage, target = m)
    }
}