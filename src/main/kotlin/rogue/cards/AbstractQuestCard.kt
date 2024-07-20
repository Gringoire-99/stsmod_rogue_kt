package rogue.cards

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.mods.DrawCardMod
import rogue.power.quest.AbstractQuestPower
import utils.addMod

abstract class AbstractQuestCard(rawName: String) :
    AbstractRogueCard(
        name = rawName,
        cost = 0,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(InnateMod())
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        p?.apply {
            val find = powers.find {
                it is AbstractQuestPower
            }
            return find == null
        }
        return true
    }

    override fun upgrade() {
        useUpgrade {
            addMod(DrawCardMod())
        }
    }

}