package rogue.cards.skill

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.GearShiftAction
import rogue.cards.AbstractRogueCard

class GearShift() :
    AbstractRogueCard(
        name = GearShift::class.simpleName.toString(),
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
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(GearShiftAction(magicNumber))
    }
}