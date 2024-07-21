package rogue.cards

interface OnCalculateCardDamage {
    fun modifyTempBaseDamage(baseDamage: IntArray)
    fun modifyTempBaseDamageMulti(baseDamages: FloatArray)
}