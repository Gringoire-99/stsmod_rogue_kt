package rogue.cards.attack

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import rogue.cards.AbstractRogueCard
import utils.dealDamage
import utils.drawCard
import utils.getRandomMonster

class Bleed() :
    AbstractRogueCard(
        name = Bleed::class.simpleName.toString(),
        cost = 0,
        type = CardType.ATTACK,
        rarity = CardRarity.SPECIAL,
        target = CardTarget.ENEMY,
        color = CardColor.COLORLESS
    ) {
    init {
        setDamage(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeDamage(2)
        }
    }

    override fun triggerWhenDrawn() {
        AbstractDungeon.player.useCard(this, getRandomMonster(), 0)
        drawCard(1)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        dealDamage(p, m, damage, damageEffect = AbstractGameAction.AttackEffect.NONE) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    it.hb.cX,
                    it.hb.cY,
                    AbstractGameAction.AttackEffect.NONE
                )
            )
        }
    }
}