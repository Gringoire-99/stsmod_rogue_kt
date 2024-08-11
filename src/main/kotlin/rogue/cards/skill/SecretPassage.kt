package rogue.cards.skill

import basemod.cardmods.ExhaustMod
import basemod.helpers.CardModifierManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.SecretPassageAction
import rogue.cards.AbstractRogueCard

class SecretPassage :
    AbstractRogueCard(
        name = SecretPassage::class.simpleName.toString(),
        cost = 1,
        type = CardType.SKILL,
        rarity = CardRarity.UNCOMMON,
        target = CardTarget.SELF
    ) {
    init {
        setMagicNumber(4)
        CardModifierManager.addModifier(this, ExhaustMod())
    }


    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(0)
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(SecretPassageAction(p = p ?: AbstractDungeon.player, draw = magicNumber))

    }
}