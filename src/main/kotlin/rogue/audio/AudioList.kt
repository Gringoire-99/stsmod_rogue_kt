package rogue.audio

import com.megacrit.cardcrawl.audio.Sfx
import com.megacrit.cardcrawl.core.Settings
import utils.modId

class AudioList(val paths: ArrayList<String>) {
    private val sfxList: ArrayList<Sfx> = paths.map { p: String ->
        Sfx("$modId/audio/sfx/${p}.wav", false)
    } as ArrayList<Sfx>


    fun play(pitchAdjust: Float = 0.1f) {
        sfxList.forEach {
            it.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0f + pitchAdjust, 0.0f)
        }
    }

    companion object {
        // why enum didn't work??
        val TessGreymanePlay = AudioList(arrayListOf("TessGreymaneBgm", "TessGreymaneSound"))
        val TessGreymaneEffect = AudioList(arrayListOf("TessGreymaneEffect"))
        val ValeeraTheHollowPlay =
            AudioList(arrayListOf("ValeeraTheHollowSound", "ValeeraTheHollowBgm1", "ValeeraTheHollowBgm2"))
        val ShadowcrafterScabbsPlay =
            AudioList(arrayListOf("ShadowcrafterScabbsSound", "ShadowcrafterScabbsBgm"))
        val SonyaWaterdancerPlay =
            AudioList(arrayListOf("SonyaWaterdancerSound", "SonyaWaterdancerBgm"))
        val SonyaWaterdancerEffect =
            AudioList(arrayListOf("SonyaWaterdancerEffect"))
        val SonyaShadowdancerPlay =
            AudioList(arrayListOf("SonyaShadowdancerSound", "SonyaShadowdancerBgm"))
        val SonyaShadowdancerEffect =
            AudioList(arrayListOf("SonyaShadowdancerEffect"))
        val SherazinCorpseFlowerPlay =
            AudioList(arrayListOf("SherazinCorpseFlowerPlay"))
        val SherazinCorpseFlowerEffect =
            AudioList(arrayListOf("SherazinCorpseFlowerEffect"))
        val VelarokTheDeceiverEffect =
            AudioList(arrayListOf("VelarokTheDeceiverEffect", "VelarokTheDeceiverBgm"))
        val VelarokTheDeceiverAttack =
            AudioList(arrayListOf("VelarokTheDeceiverAttackBgm", "VelarokTheDeceiverAttackSound"))
        val VelarokWindbladeAttack =
            AudioList(arrayListOf("VelarokWindbladeAttack"))
        val EdwinVanCleefAttack =
            AudioList(arrayListOf("EdwinVanCleefAttack"))
        val MaestraMaskMerchantPlay =
            AudioList(arrayListOf("MaestraMaskMerchantSound", "MaestraMaskMerchantBgm1", "MaestraMaskMerchantBgm2"))
        val MaestraMaskMerchantEffect =
            AudioList(arrayListOf("MaestraMaskMerchantBgm2"))
        val MyraRotspringPlay =
            AudioList(arrayListOf("MyraRotspringSound", "MyraRotspringBgm"))
    }

}