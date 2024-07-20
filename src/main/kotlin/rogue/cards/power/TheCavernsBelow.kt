package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractQuestCard
import rogue.power.quest.TheCavernsBelowPower
import utils.applyUniquePower

class TheCavernsBelow : AbstractQuestCard(TheCavernsBelow::class.simpleName.toString()) {
    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            applyUniquePower(p, p, TheCavernsBelowPower(p))
        }
    }
}