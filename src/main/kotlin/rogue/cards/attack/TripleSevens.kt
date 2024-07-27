package rogue.cards.attack

import basemod.cardmods.RetainMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractRogueCard
import rogue.cards.UpgradeInterface
import utils.addMod
import utils.dealDamage
import utils.drawCard

class TripleSevens() :
    AbstractRogueCard(
        name = TripleSevens::class.simpleName.toString(),
        cost = 7,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ENEMY
    ), UpgradeInterface {
    init {
        setDamage(7)
        setMagicNumber(7)
    }

    override var upgradeCount: Int = 0
        set(value) {
            field = value
            if (field >= maxUpgradeCount) {
                onMaxUpgrade()
                field = 0
            }
        }

    override fun triggerOnOtherCardPlayed(c: AbstractCard?) {
        upgradeCount++
    }

    override val maxUpgradeCount: Int = 7
    override var level: Int = 1
    override fun onMaxUpgrade() {
        freeToPlayOnce = true
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
        copy.level = level
        copy.upgradeCount = upgradeCount
        return copy
    }
}