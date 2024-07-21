package rogue.patchs

import com.evacipated.cardcrawl.modthespire.lib.ByRef
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.megacrit.cardcrawl.cards.AbstractCard
import rogue.cards.OnCalculateCardDamage

class WeaponCardPatch {
    @SpirePatch2(clz = AbstractCard::class, method = "calculateCardDamage")
    internal class BeforeCalculateCardDamage {
        companion object {
            @JvmStatic
            @SpireInsertPatch(rloc = 9, localvars = ["tmp"])
            fun insert(__instance: AbstractCard, @ByRef tmp: IntArray) {
                if (__instance is OnCalculateCardDamage) {
                    __instance.modifyTempBaseDamage(tmp)
                }
            }
        }
    }

    @SpirePatch2(clz = AbstractCard::class, method = "calculateCardDamage")
    internal class BeforeCalculateCardDamageMulti {
        companion object {
            @JvmStatic
            @SpireInsertPatch(rloc = 65, localvars = ["tmp"])
            fun insert(__instance: AbstractCard, tmp: FloatArray) {
                if (__instance is OnCalculateCardDamage) {
                    __instance.modifyTempBaseDamageMulti(tmp)
                }
            }
        }
    }
}