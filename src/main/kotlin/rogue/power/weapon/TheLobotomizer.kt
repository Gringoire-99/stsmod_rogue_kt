package rogue.power.weapon

//class TheLobotomizer(
//    damage: Int = 3,
//    duration: Int = 2,
//    upgraded: Boolean = false,
//    magic: Int = 4
//) : AbstractWeaponPower(
//    rawName = TheLobotomizer::class.simpleName.toString(),
//    damage = damage,
//    duration = duration,
//    upgraded = upgraded,
//    magic = magic
//) {
//    init {
//
//        damageModifier.cbOfOnLastDamageTakenUpdate.add { _: DamageInfo?,
//                                                         lastDamageTaken: Int,
//                                                         _: Int,
//                                                         target: AbstractCreature? ->
//            if (lastDamageTaken > 0) {
//                (target as AbstractMonster).apply {
//                    addToBot(ApplyPowerAction(this, owner, TheLobotomizerPower(this, magic), magic))
//                }
//            }
//        }
//    }
//
//    override fun makeCopy(): AbstractWeaponPowerCard {
//        val c = rogue.cards.power.TheLobotomizer()
//        if (upgraded) c.upgrade()
//        return c
//    }
//}