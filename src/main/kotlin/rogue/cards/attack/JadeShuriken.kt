//package rogue.cards.attack
//
//import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
//import com.megacrit.cardcrawl.characters.AbstractPlayer
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon
//import com.megacrit.cardcrawl.monsters.AbstractMonster
//import rogue.cards.AbstractComboCard
//import rogue.power.JadeGrowth
//import utils.dealDamage
//import utils.makeId
//
//class JadeShuriken :
//    AbstractComboCard(
//        name = JadeShuriken::class.simpleName.toString(),
//        cost = 2,
//        type = CardType.ATTACK,
//        rarity = CardRarity.UNCOMMON,
//        target = CardTarget.ENEMY
//    ) {
//
//    init {
//        setMagicNumber(2)
//        setDamage(11)
//    }
//
//    override fun upgrade() {
//        useUpgrade {
//            upgradeDamage(3)
//            upgradeMagicNumber(1)
//        }
//    }
//
//    override fun calculateCardDamage(mo: AbstractMonster?) {
//        val power = AbstractDungeon.player?.getPower(JadeGrowth::class.makeId())
//        power?.apply {
//            baseDamage += power.amount
//        }
//        super.calculateCardDamage(mo)
//        power?.apply {
//            baseDamage -= power.amount
//        }
//    }
//
//
//    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
//        dealDamage(p, m, damage)
//        useCombo {
//            p?.apply {
//                val filter = hand.group.filter {
//                    it is JadeShuriken && it != this
//                }
//                utils.logger.info("combo ${filter.size}")
//                filter.forEach { p.useCard(it, m, 0) }
//            }
//        }
//        addToBot(ApplyPowerAction(p, p, JadeGrowth(p ?: AbstractDungeon.player, magicNumber), magicNumber))
//    }
//}