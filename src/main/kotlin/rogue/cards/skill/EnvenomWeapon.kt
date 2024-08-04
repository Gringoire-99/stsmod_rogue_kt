package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.getWeaponPower
import utils.isWeaponEquipped
import utils.makeId

class EnvenomWeapon :
    AbstractRogueCard(
        name = EnvenomWeapon::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        type = CardType.SKILL,
        target = CardTarget.SELF
    ) {
    val mod = ExhaustMod()

    companion object {
        val name = CardCrawlGame.languagePack.getCardStrings(EnvenomWeapon::class.makeId()).NAME
    }

    init {
        setMagicNumber(20)
        exhaust = true
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(10)
            exhaust = false
        }
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(EmptyAction {
            p?.getWeaponPower()?.apply {
                poisonCount += magicNumber
            }
        })
    }
}