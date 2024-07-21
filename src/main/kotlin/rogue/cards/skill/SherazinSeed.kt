package rogue.cards.skill

import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.OnExhaustInterface
import rogue.cards.UpgradeInterface
import rogue.cards.attack.SherazinCorpseFlower
import utils.addMod

class SherazinSeed :
    AbstractRogueCard(
        name = SherazinSeed::class.simpleName.toString(),
        cost = -2,
        type = CardType.SKILL,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.NONE,
        color = CardColor.COLORLESS
    ), OnExhaustInterface, UpgradeInterface {
    init {
        addMod(RetainMod())
        PurgeField.purge.set(this, true)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
    }

    override var upgradeCount: Int = 0
    override val maxUpgradeCount: Int = 6
    override var level: Int = 0

    override fun onMaxUpgrade() {
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return false
    }

    override fun afterCardExhausted() {
        upgradeCount++
        if (upgradeCount >= maxUpgradeCount) {
            upgradeCount = 0
            val flower = SherazinCorpseFlower()
            if (upgraded) {
                flower.upgrade()
            }
            flower.freeToPlayOnce = true
            addToBot(MakeTempCardInHandAction(flower))
            addToBot(ExhaustSpecificCardAction(this, AbstractDungeon.player.hand))
        }
    }
}