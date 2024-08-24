package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.cards.skill.Cheatsheet
import rogue.power.secret.PlagiarizePower
import utils.makeId

class Plagiarize :
    AbstractSecretCard(
        rawName = Plagiarize::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.COMMON,
        powerId = PlagiarizePower::class.makeId()
    ) {
    init {
        cardsToPreview = Cheatsheet.preview
    }


    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyUniquePowerAction(PlagiarizePower(p ?: AbstractDungeon.player, upgraded = upgraded)))
    }
}