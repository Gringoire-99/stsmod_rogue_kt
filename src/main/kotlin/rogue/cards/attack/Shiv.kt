package rogue.cards.attack

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.PoisonPower
import rogue.cards.AbstractRogueCard
import rogue.mods.DamageEffectMod
import utils.dealDamage

class Shiv :
    AbstractRogueCard(
        name = Shiv::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(4)
        DamageModifierManager.addModifier(this, DamageEffectMod { amount, target ->
            addToBot(
                ApplyPowerAction(
                    target,
                    AbstractDungeon.player,
                    PoisonPower(target, AbstractDungeon.player, amount),
                    amount
                )
            )

        })
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
    }
}