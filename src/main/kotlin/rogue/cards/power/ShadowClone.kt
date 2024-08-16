package rogue.cards.power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.cards.AbstractSecretCard
import rogue.power.secret.ShadowClonePower
import utils.makeId

class ShadowClone() :
    AbstractSecretCard(
        rawName = ShadowClone::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = ShadowClonePower::class.makeId()
    ) {


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyPowerAction(p, p, ShadowClonePower(p ?: AbstractDungeon.player, max = if (upgraded) 2 else 3)))
    }
}