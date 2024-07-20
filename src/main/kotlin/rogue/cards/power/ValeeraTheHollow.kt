package rogue.cards.power

import basemod.cardmods.InnateMod
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyHeroPowerAction
import rogue.cards.AbstractRogueCard
import rogue.power.hero.DeathsShadowPower
import utils.addMod
import utils.gainBlock

class ValeeraTheHollow() :
    AbstractRogueCard(
        name = ValeeraTheHollow::class.simpleName.toString(),
        cost = 3,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {


    override fun upgrade() {
        useUpgrade {
            addMod(InnateMod())
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        gainBlock(p, 5)
        p?.apply {
            addToBot(ApplyHeroPowerAction(this, DeathsShadowPower(this)))
        }
    }
}