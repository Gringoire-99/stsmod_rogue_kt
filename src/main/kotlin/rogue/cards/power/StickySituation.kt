package rogue.cards.power

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import rogue.action.ApplyUniquePowerAction
import rogue.cards.AbstractSecretCard
import rogue.characters.Rogue
import rogue.power.secret.StickySituationPower
import utils.makeId

class StickySituation() :
    AbstractSecretCard(
        rawName = StickySituation::class.simpleName.toString(),
        cost = 1,
        rarity = CardRarity.UNCOMMON,
        powerId = StickySituationPower::class.makeId()
    ) {
    init {
        setMagicNumber(2)
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(1)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToBot(ApplyUniquePowerAction(StickySituationPower(p ?: AbstractDungeon.player, magicNumber)) {
            Rogue.sneakAttackPredicates.add(Rogue.SneakAttackPredicate(powerId) { mo ->
                mo.powers.count { it.type == AbstractPower.PowerType.DEBUFF } > 1
            })
        })
    }
}