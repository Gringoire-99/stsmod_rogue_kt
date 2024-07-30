package rogue.cards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.characters.RogueEnum

abstract class AbstractMimicCard(
    name: String,
    cost: Int,
    type: CardType,
    rarity: CardRarity,
    target: CardTarget = CardTarget.NONE,
    color: CardColor = RogueEnum.HS_ROGUE_CARD_COLOR
) :
    AbstractRogueCard(
        name, cost, type,
        rarity,
        target,
        color
    ) {
    var mimicTarget: AbstractCard? = null
    fun mimic(c: AbstractCard) {
        val copy = c.makeStatEquivalentCopy()
        copy.name = name
        cardID = copy.cardID
        target = copy.target
        type = copy.type
        cost = copy.cost
        costForTurn = copy.costForTurn
        cardsToPreview = copy.cardsToPreview
        this.retain = copy.retain || this.retain
        this.selfRetain = copy.selfRetain || this.retain
        this.exhaust = copy.exhaust || this.exhaust
        this.isEthereal = copy.isEthereal || this.isEthereal
        mimicTarget = copy
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return mimicTarget?.canUse(p, m) ?: false
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        mimicTarget?.use(p, m)
    }

    override fun render(sb: SpriteBatch?) {
        if (mimicTarget != null) {
            mimicTarget?.let {
                val updateGlow = AbstractCard::class.java.getDeclaredMethod("updateGlow")
                updateGlow.isAccessible = true
                updateGlow.invoke(this)
                val renderGlow = AbstractCard::class.java.getDeclaredMethod("renderGlow", SpriteBatch::class.java)
                renderGlow.isAccessible = true
                renderGlow.invoke(this, sb)
                it.current_x = current_x
                it.current_y = current_y
                it.drawScale = drawScale
                it.angle = angle
                it.render(sb)
            }
        } else {
            super.render(sb)
        }
    }

    override fun renderCardTip(sb: SpriteBatch?) {
        if (mimicTarget != null) {
            mimicTarget?.let {
                val renderTip = AbstractCard::class.java.getDeclaredField("renderTip")
                renderTip.isAccessible = true
                renderTip.set(it, renderTip.get(this))
                it.renderCardTip(sb)
            }
        } else {
            super.renderCardTip(sb)
        }
    }

    override fun makeStatEquivalentCopy(): AbstractCard {
        val copy = super.makeStatEquivalentCopy() as AbstractMimicCard
        copy.mimicTarget = this.mimicTarget
        copy.cardID = this.cardID
        return copy
    }
}