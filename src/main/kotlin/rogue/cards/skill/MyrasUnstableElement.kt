package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.cardmods.RetainMod
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.MyrasUnstableElementAction
import rogue.cards.AbstractRogueCard
import utils.addMod

class MyrasUnstableElement :
    AbstractRogueCard(
        name = MyrasUnstableElement::class.simpleName.toString(),
        cost = 0,
        type = CardType.SKILL,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(ExhaustMod())
        GraveField.grave.set(this, true)
    }

    override fun upgrade() {
        useUpgrade {
            addMod(RetainMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        p?.apply {
            addToBot(MyrasUnstableElementAction(p))
        }
    }
}