package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import rogue.cards.LevelInterface
import rogue.cards.impls.LevelInterfaceImpl
import rogue.mods.ReduceCostMod
import utils.*

class VelarokWindblade(impl: LevelInterface = LevelInterfaceImpl(maxExpr = 1, maxLevel1 = 2)) :
    AbstractRogueCard(
        name = VelarokWindblade::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.RARE,
        target = CardTarget.ENEMY
    ), LevelInterface by impl {
    init {
        setDamage(6)
        setMagicNumber(6)
        addMod(RetainMod())
        impl.onMaxLevelCb = {
            isRealForm = true
            upgradeDamage(magicNumber)
            name = cardString.EXTENDED_DESCRIPTION[0]
            loadCardImage("$modId/cards/attack/VelarokTheDeceiver.png")
            rawDescription = cardString.EXTENDED_DESCRIPTION[1]
            initializeDescription()
            initializeTitle()
        }
        cardsToPreview = preview
    }

    companion object {
        val preview = VelarokWindblade().apply { level = 2 }
    }

    private var isRealForm = false
    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        c?.let {
            if (it.isOtherClassCard()) {
                exp++
            }
        }
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(4)
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage)
        if (isRealForm) {
            discovery(CardFilter(cardRarity = hashSetOf(CardRarity.RARE, CardRarity.UNCOMMON))) {
                it.addMod(ReduceCostMod(2))
            }
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as VelarokWindblade
        copy.level = level
        return copy
    }
}