package rogue.power.common

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.PlayTwiceAction
import rogue.power.IAbstractPower
import utils.isOtherClassCard

class PlayTwicePower(owner: AbstractPlayer, stackAmount: Int = 1) :
    IAbstractPower(powerName = PlayTwicePower::class.simpleName.toString(), owner = owner, amount = stackAmount) {
    override fun onUseCard(usedCard: AbstractCard?, action: UseCardAction?) {
        usedCard?.apply {
            if (!purgeOnUse && this.isOtherClassCard() && amount > 0) {

                var m: AbstractMonster? = null
                if (action?.target != null) {
                    m = action.target as AbstractMonster
                }

                addToBot(PlayTwiceAction(this, m, purge = true))
                amount--
                if (amount <= 0) {
                    addToBot(RemoveSpecificPowerAction(owner, owner, this@PlayTwicePower))
                }
            }
        }

    }


}