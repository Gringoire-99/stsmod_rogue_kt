package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractQuestCard
import rogue.power.quest.TheCavernsBelowPower
import utils.applyUniqueAndStablePower

class TheCavernsBelow : AbstractQuestCard(TheCavernsBelow::class.simpleName.toString()) {
    init {
        cardsToPreview = CrystalCore.preview
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            applyUniqueAndStablePower(TheCavernsBelowPower(p))
        }
    }
}