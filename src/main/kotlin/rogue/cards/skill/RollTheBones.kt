package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import com.megacrit.cardcrawl.actions.utility.ScryAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import rogue.mods.FateMod
import utils.addMod
import utils.drawCard

class RollTheBones() :
    AbstractRogueCard(
        name = RollTheBones::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
        addMod(ExhaustMod())
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ScryAction(magicNumber))
        addToBot(EmptyAction {
            val last = AbstractDungeon.player.drawPile.group.lastOrNull()
            last?.addMod(FateMod(this))
        })
        drawCard(1)
    }
}