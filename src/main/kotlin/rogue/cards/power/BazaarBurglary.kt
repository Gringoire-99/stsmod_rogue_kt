package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractQuestCard
import rogue.power.quest.BazaarBurglaryPower

class BazaarBurglary :
    AbstractQuestCard(
        rawName = BazaarBurglary::class.simpleName.toString()
    ) {

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, BazaarBurglaryPower(p ?: AbstractDungeon.player)))
    }
}