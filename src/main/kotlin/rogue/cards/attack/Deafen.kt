package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.DeafenAction
import rogue.cards.AbstractComboCard
import utils.addMod
import utils.dealDamage
import utils.getRandomMonster

class Deafen() :
    AbstractComboCard(
        name = Deafen::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(6)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(3)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        useCombo {
            dealDamage(p, m, damage)
        }
        addToBot(
            DeafenAction(
                p ?: AbstractDungeon.player,
                m ?: getRandomMonster()
            )
        )
    }
}