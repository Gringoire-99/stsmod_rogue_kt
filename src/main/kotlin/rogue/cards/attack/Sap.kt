package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.GainStrengthPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.powers.WeakPower
import rogue.cards.AbstractRogueCard
import rogue.mods.DamageEffectMod
import utils.dealDamage

class Sap() :
    AbstractRogueCard(
        name = Sap::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(3)
        DamageModifierManager.addModifier(this, DamageEffectMod { amount, target ->
            addToBot(ApplyPowerAction(target, AbstractDungeon.player, StrengthPower(target, -amount), -amount))
            addToBot(ApplyPowerAction(target, AbstractDungeon.player, GainStrengthPower(target, amount), amount))
            addToBot(ApplyPowerAction(target, AbstractDungeon.player, WeakPower(target, amount, false), amount))
        })
        CardModifierManager.addModifier(this,ExhaustMod())
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