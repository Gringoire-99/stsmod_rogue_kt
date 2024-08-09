package rogue.cards.power

import basemod.cardmods.RetainMod
import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import common.CardFilter
import rogue.audio.CardAudioList
import rogue.cards.AbstractMimicCard
import utils.addMod
import utils.generateCardChoices
import kotlin.math.min

class MaestraMaskMerchant() :
    AbstractMimicCard(
        name = MaestraMaskMerchant::class.simpleName.toString(),
        cost = 2,
        type = CardType.POWER,
        rarity = CardRarity.RARE,
        target = CardTarget.SELF
    ) {
    init {
        addMod(RetainMod())
        tags.add(BaseModCardTags.FORM)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeBaseCost(1)
        }
    }

    override fun atTurnStartPreDraw() {
        mimicToRandomForm()
    }

    private fun mimicToRandomForm() {
        val take = generateCardChoices(CardFilter(includeTags = hashSetOf(BaseModCardTags.FORM)), 1).firstOrNull()
        take?.let {
            it.cost = min(it.cost, this.cost)
            it.costForTurn = min(it.costForTurn, this.costForTurn)
            it.freeToPlayOnce = freeToPlayOnce || it.freeToPlayOnce
            if (upgraded && !it.upgraded) it.upgrade()
            this.mimic(it)
            this.isEthereal = false
            retain = true
            selfRetain = true
        }
        CardAudioList.MaestraMaskMerchantEffect.play()
    }

    override fun triggerWhenDrawn() {
        mimicToRandomForm()
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        CardAudioList.MaestraMaskMerchantPlay.play()
        super.use(p, m)
    }

}