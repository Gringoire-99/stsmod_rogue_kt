package rogue.cards.skill

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.DredgeAction
import rogue.cards.AbstractComboCard
import utils.addMod
import utils.drawCard

class GoneFishin() :
    AbstractComboCard(
        name = GoneFishin::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.COMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(3)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(3)
            addMod(InnateMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(DredgeAction(magic = magicNumber))
        useCombo {
            drawCard(1)
        }
    }
}