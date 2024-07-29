//package rogue.cards.skill
//
//import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
//import com.megacrit.cardcrawl.characters.AbstractPlayer
//import com.megacrit.cardcrawl.monsters.AbstractMonster
//import rogue.cards.AbstractRogueCard
//import rogue.cards.attack.Bleed
//
//class Garrote() :
//    AbstractRogueCard(
//        name = Garrote::class.simpleName.toString(),
//        cost = 1,
//        type = CardType.SKILL,
//        rarity = CardRarity.UNCOMMON,
//        target = CardTarget.SELF
//    ) {
//    init {
//        setMagicNumber(2)
//    }
//
//    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
//        addToBot(MakeTempCardInDiscardAction(Bleed().apply {
//            if (this@Garrote.upgraded) {
//                this.upgrade()
//            }
//        }, magicNumber))
//    }
//}