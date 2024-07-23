package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractComboCard
import rogue.cards.OnCalculateCardDamage
import utils.addMod
import utils.dealDamage

class EdwinVanCleef() :
    AbstractComboCard(
        name = EdwinVanCleef::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.RARE,
        target = CardTarget.ENEMY
    ), OnCalculateCardDamage {
    init {
        setDamage(6)
        setMagicNumber(1)
        addMod(RetainMod(), ExhaustMod())
    }

    var additionalDamage = 0
    override fun atTurnStart() {
        additionalDamage = 0
    }

    override fun modifyTempBaseDamage(baseDamage: IntArray) {
        additionalDamage = AbstractDungeon.actionManager.cardsPlayedThisTurn.size * magicNumber
        baseDamage[0] = baseDamage[0] + additionalDamage
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        useCombo {
            damage += additionalDamage
        }
        dealDamage(p, m, damage)
    }
}