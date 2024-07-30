package rogue.cards.skill

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.OnExhaustInterface
import rogue.cards.attack.SherazinCorpseFlower
import rogue.cards.impls.LevelInterfaceImpl
import utils.addMod

class SherazinSeed(
    private val upgradeImpl: LevelInterface =
        LevelInterfaceImpl(maxExpr = 4, maxLevel1 = 2)
) :
    AbstractRogueCard(
        name = SherazinSeed::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ), OnExhaustInterface, LevelInterface by upgradeImpl {
    init {
        addMod(RetainMod())
        cardsToPreview = SherazinCorpseFlower()
        upgradeImpl.onMaxLevelCb = {
            resetLevel()
            val flower = SherazinCorpseFlower()
            if (upgraded) {
                flower.upgrade()
            }
            flower.freeToPlayOnce = true
            addToBot(MakeTempCardInHandAction(flower))
            addToBot(ExhaustSpecificCardAction(this, AbstractDungeon.player.hand))
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return false
    }

    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {
    }

    override fun afterCardExhausted() {
        exp++
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as SherazinSeed
        copy.exp = this.exp
        return copy
    }
}