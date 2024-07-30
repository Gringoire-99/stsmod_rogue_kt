package rogue.cards.attack

import basemod.cardmods.ExhaustMod
import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.BiteEffect
import rogue.cards.AbstractRogueCard
import rogue.cards.skill.SherazinSeed
import utils.addMod
import utils.dealDamage

class SherazinCorpseFlower :
    AbstractRogueCard(
        name = SherazinCorpseFlower::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.RARE,
        target = CardTarget.ENEMY
    ) {
    init {
        setDamage(4)
        setMagicNumber(3)
        addMod(ExhaustMod())
        cardsToPreview = SherazinSeed()
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun triggerOnExhaust() {
        val seed = SherazinSeed()
        if (upgraded) {
            seed.upgrade()
        }
        addToBot(MakeTempCardInHandAction(seed))
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        repeat(magicNumber) {
            if (m != null) {
                this.addToBot(
                    VFXAction(
                        BiteEffect(m.hb.cX, m.hb.cY - 40.0f * Settings.scale, Color.SCARLET.cpy()),
                        0.1f
                    )
                )
                dealDamage(p, m, damage)
            }
        }
    }
}