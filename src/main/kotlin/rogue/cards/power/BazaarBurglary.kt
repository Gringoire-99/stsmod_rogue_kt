package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractQuestCard
import rogue.cards.attack.MirageBlade
import rogue.power.quest.BazaarBurglaryPower
import utils.applyUniqueAndStablePower

class BazaarBurglary :
    AbstractQuestCard(
        rawName = BazaarBurglary::class.simpleName.toString()
    ) {
    init {
        cardsToPreview = MirageBlade.preview
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            applyUniqueAndStablePower(BazaarBurglaryPower(p))
        }
    }
}