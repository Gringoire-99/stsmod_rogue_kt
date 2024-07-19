package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect
import rogue.cards.AbstractRogueCard
import rogue.cards.OnExhaustInterface
import rogue.cards.UpgradeInterface
import utils.addMod
import utils.getRandomMonster
import utils.modId

class LesserOnyxSpellstone() :
    AbstractRogueCard(
        name = LesserOnyxSpellstone::class.simpleName.toString(),
        cost = 3,
        type = CardType.ATTACK,
        rarity = CardRarity.RARE,
        target = CardTarget.ALL_ENEMY
    ), UpgradeInterface, OnExhaustInterface {
    init {
        setDamage(9)
        setMagicNumber(2)
        addMod(RetainMod(), ExhaustMod())
        onLevelUp()
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(4)
        }
    }

    override var level: Int = 1
    override val maxUpgradeCount: Int = 6

    override var upgradeCount = 0
        set(value) {
            if (level < 3) {
                field = value
                if (field >= maxUpgradeCount) {
                    field = 0
                    level++
                    onMaxUpgrade()
                }
            }

        }

    override fun onMaxUpgrade() {
        upgradeMagicNumber(2)
        onLevelUp()
    }

    fun onLevelUp() {
        if (level == 2) {
            this.name = "法术黑曜石"
            this.loadCardImage("$modId/cards/attack/OnyxSpellstone.png")
            initializeTitle()
        } else if (level == 3) {
            this.name = "大型法术黑曜石"
            this.loadCardImage("$modId/cards/attack/GreaterOnyxSpellstone.png")
            initializeTitle()
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        repeat(magicNumber) {
            val rm = getRandomMonster()
            p?.apply {
                rm.apply {
                    addToBot(VFXAction(HemokinesisEffect(p.hb.cX, p.hb.cY, rm.hb.cX, rm.hb.cY), 0.5f))
                    addToBot(AttackDamageRandomEnemyAction(this@LesserOnyxSpellstone, AttackEffect.BLUNT_HEAVY))
                }
            }
        }

    }

    override fun afterCardExhausted() {
        upgradeCount++
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as LesserOnyxSpellstone
        copy.level = level
        copy.onLevelUp()
        return copy
    }
}