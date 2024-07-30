package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.impls.LevelInterfaceImpl
import utils.addMod
import utils.dealDamage
import utils.drawCard

class TripleSevens(
    private val upgradeImpl: LevelInterface =
        LevelInterfaceImpl(maxExpr = 7, maxLevel1 = 2)
) :
    AbstractRogueCard(
        name = TripleSevens::class.simpleName.toString(),
        cost = 7,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ), LevelInterface by upgradeImpl {
    init {
        upgradeImpl.onMaxLevelCb = {
            freeToPlayOnce = true
            resetLevel()
        }
        setDamage(7)
        setMagicNumber(7)
    }


    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        if (!freeToPlayOnce) {
            exp++
        }
    }


    override fun upgrade() {
        useUpgrade {
            addMod(RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
        drawCard(magicNumber)
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as TripleSevens
        copy.exp = exp
        return copy
    }
}