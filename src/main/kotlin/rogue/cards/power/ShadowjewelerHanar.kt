package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.cards.AbstractRogueCard
import rogue.cards.AbstractSecretCard
import rogue.power.common.ShadowSecretPower
import utils.discovery

class ShadowjewelerHanar() :
    AbstractRogueCard(
        name = ShadowjewelerHanar::class.simpleName.toString(),
        cost = 3,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(1)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val f = CardFilter(predicate = { it is AbstractSecretCard })
        discovery(f)
        addToBot(ApplyPowerAction(p, p, ShadowSecretPower(p ?: AbstractDungeon.player, magicNumber), magicNumber))
    }
}