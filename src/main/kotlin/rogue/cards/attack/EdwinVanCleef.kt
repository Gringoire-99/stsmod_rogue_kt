package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.OnCalculateCardDamage
import utils.addMod
import utils.dealDamage

class EdwinVanCleef() :
    AbstractRogueCard(
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

    override fun modifyTempBaseDamage(baseDamage: IntArray) {
        baseDamage[0] = baseDamage[0] + AbstractDungeon.actionManager.cardsPlayedThisTurn.size * magicNumber
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
    }
}