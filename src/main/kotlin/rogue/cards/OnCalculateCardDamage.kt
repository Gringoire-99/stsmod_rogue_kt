package rogue.cards

import com.megacrit.cardcrawl.monsters.AbstractMonster

interface OnCalculateCardDamage {
    fun modifyTempBaseDamage(baseDamage: IntArray, mo: AbstractMonster?) {

    }

    fun modifyTempBaseDamageMulti(baseDamages: FloatArray, mo: AbstractMonster?) {

    }
}