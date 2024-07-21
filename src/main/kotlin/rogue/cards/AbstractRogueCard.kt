package rogue.cards

import basemod.abstracts.CustomCard
import rogue.characters.RogueEnum
import utils.getCardImg
import utils.getCardString
import utils.makeId

abstract class AbstractRogueCard(
    name: String,
    cost: Int,
    type: CardType,
    rarity: CardRarity,
    target: CardTarget,
    color: CardColor = RogueEnum.HS_ROGUE_CARD_COLOR
) : CustomCard(
    name.makeId(),
    getCardString(name).NAME,
    getCardImg(type, name),
    cost,
    getCardString(name).DESCRIPTION,
    type,
    color,
    rarity,
    target
) {

    fun setDamage(d: Int) {
        this.baseDamage = d
        this.damage = d
    }

    fun setBlock(b: Int) {
        this.baseBlock = b
        this.block = b
    }

    fun setMagicNumber(m: Int) {
        this.baseMagicNumber = m
        this.magicNumber = m
    }

    override fun upgrade() {
        useUpgrade { }
    }

    fun useUpgrade(effect: () -> Unit) {
        if (!upgraded) {
            upgradeName()
            effect()
            this.upgraded = true
        }
    }


}