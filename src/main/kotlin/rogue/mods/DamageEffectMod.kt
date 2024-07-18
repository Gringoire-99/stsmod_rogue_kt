package rogue.mods

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature

class DamageEffectMod(
    private val isInherent: Boolean = true,
    val effect: (amount: Int, target: AbstractCreature?) -> Unit = { _, _ ->
    }
) :
    AbstractDamageModifier() {
    override fun onLastDamageTakenUpdate(
        info: DamageInfo?,
        lastDamageTaken: Int,
        overkillAmount: Int,
        target: AbstractCreature?
    ) {
        if (lastDamageTaken > 0) {
            effect(lastDamageTaken, target)
        }
    }

    override fun makeCopy(): AbstractDamageModifier {
        return DamageEffectMod()
    }

    override fun isInherent(): Boolean {
        return isInherent
    }
}