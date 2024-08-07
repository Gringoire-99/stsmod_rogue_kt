package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.OnExhaustInterface
import rogue.cards.impls.LevelInterfaceImpl
import utils.addMod
import utils.dealDamage
import utils.getRandomMonster
import utils.modId

class LesserOnyxSpellstone(
    private val upgradeImpl: LevelInterface =
        LevelInterfaceImpl(maxExpr = 8, maxLevel1 = 99)
) :
    AbstractRogueCard(
        name = LesserOnyxSpellstone::class.simpleName.toString(),
        cost = 3,
        type = CardType.ATTACK,
        rarity = CardRarity.RARE,
        target = CardTarget.ALL_ENEMY
    ), LevelInterface by upgradeImpl, OnExhaustInterface {


    init {
        upgradeImpl.onLevelUpCb = {
            if (level == 2) {
                this.name = cardString.EXTENDED_DESCRIPTION[0]
                this.loadCardImage("$modId/cards/attack/OnyxSpellstone.png")
                initializeTitle()
            } else if (level == 3) {
                this.name = cardString.EXTENDED_DESCRIPTION[1]
                this.loadCardImage("$modId/cards/attack/GreaterOnyxSpellstone.png")
                initializeTitle()
            }
        }
        upgradeImpl.onMaxExpCb = {
            upgradeMagicNumber(if (level < 3) 2 else 1)
            upgradeDamage(magicNumber)
        }
        setDamage(12)
        setMagicNumber(2)
        addMod(RetainMod(), ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(4)
        }
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        repeat(magicNumber) {
            addToBot(EmptyAction {
                val rm = getRandomMonster()
                p?.apply {
                    rm?.apply {
                        dealDamage(
                            p,
                            rm,
                            damage = this@LesserOnyxSpellstone.damage,
                            damageEffect = AttackEffect.BLUNT_HEAVY
                        )
                        addToTop(VFXAction(HemokinesisEffect(p.hb.cX, p.hb.cY, rm.hb.cX, rm.hb.cY), 0.5f))
                    }
                }
            })

        }

    }

    override fun afterCardExhausted() {
        utils.logger.info("============$name $exp card exhausted =======")
        if (!isMaxLevel()) {
            exp++
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as LesserOnyxSpellstone
        copy.level = level
        copy.exp = exp
        return copy
    }

    override fun makeCopy(): AbstractCard {
        return LesserOnyxSpellstone()
    }

}