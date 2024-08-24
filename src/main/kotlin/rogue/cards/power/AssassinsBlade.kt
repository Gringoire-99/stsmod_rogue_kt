package rogue.cards.power

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EquipWeaponAction
import rogue.cards.AbstractWeaponPowerCard
import rogue.characters.Rogue
import utils.dealDamage
import utils.makeId

class AssassinsBlade(
    wDurability: Int = 5,
    wDamage: Int = 3,
    magic: Int = 3
) :
    AbstractWeaponPowerCard(
        name = AssassinsBlade::class.simpleName.toString(),
        cost = 2,
        rarity = CardRarity.COMMON,
        initialDurability = wDurability,
        initialDamage = wDamage

    ) {
    init {
        setMagicNumber(magic)
    }

    override fun upgrade() {
        useUpgrade {
            weaponDamage += 2
            weaponDurability++
            upgradeMagicNumber(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val magic = magicNumber
        addToBot(
            EquipWeaponAction(
                rogue.power.weapon.AssassinsBlade(
                    damage = weaponDamage,
                    durability = weaponDurability,
                    magic = magicNumber,
                    upgraded = upgraded
                )
            ) {
                Rogue.onSneakAttack.add(Rogue.OnSneakAttack(rogue.power.weapon.AssassinsBlade::class.makeId()) {
                    dealDamage(p, it, magic, damageEffect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
                })
            }
        )
    }
}