package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect
import rogue.cards.AbstractComboCard
import rogue.cards.LevelInterface
import rogue.cards.OnExhaustInterface
import rogue.cards.impls.LevelInterfaceImpl
import utils.addMod
import utils.dealDamage
import utils.modId

class WickedStabRank1(impl: LevelInterface = LevelInterfaceImpl(maxExpr = 4, maxLevel1 = 3)) :
    AbstractComboCard(
        name = WickedStabRank1::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ), LevelInterface by impl, OnExhaustInterface {
    init {
        addMod(RetainMod())
        setDamage(9)
        setMagicNumber(4)
        impl.onMaxExpCb = {
            upgradeDamage(magicNumber)
        }
        impl.onLevelUpCb = {
            when (level) {
                2 -> {
                    this.name = "邪恶挥刺（等级2）"
                    this.loadCardImage("$modId/cards/attack/WickedStabRank2.png")
                    initializeTitle()
                }

                3 -> {
                    this.name = "邪恶挥刺（等级3）"
                    this.loadCardImage("$modId/cards/attack/WickedStabRank3.png")
                    initializeTitle()
                }
            }
        }
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        if (!useCombo {
                addToTop(SFXAction("ATTACK_HEAVY"))
                addToTop(VFXAction(p, CleaveEffect(), 0.1f))
                addToBot(
                    DamageAllEnemiesAction(
                        p,
                        damage,
                        DamageInfo.DamageType.NORMAL,
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                    )
                )
            }) {
            dealDamage(p, m, damage)
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as WickedStabRank1
        copy.level = level
        copy.exp = exp
        return copy
    }

    override fun afterCardExhausted() {
        if (!isMaxLevel()) {
            exp++
        }
    }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        if (c is AbstractComboCard && !isMaxLevel()) {
            exp++
        }
    }
}