package rogue.audio

import com.megacrit.cardcrawl.audio.Sfx
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rogue.modconfig.RogueModConfig
import utils.modId

abstract class AudioList(paths: ArrayList<String>) {
    protected val sfxList: ArrayList<Sfx> = paths.map { p: String ->
        Sfx("$modId/audio/sfx/${p}.wav", false)
    } as ArrayList<Sfx>


    open fun play(pitchAdjust: Float = 0.1f) {
        sfxList.forEach {
            it.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0f + pitchAdjust, 0.0f)
        }
    }
}

class CommonAudioList(paths: ArrayList<String>) : AudioList(paths) {
    companion object {
        val ValeeraChoose = CharacterAudioList(arrayListOf("ValeeraChoose"))
    }
}

class CharacterAudioList(paths: ArrayList<String>) : AudioList(paths) {
    companion object {
        val ValeeraDeath = CharacterAudioList(arrayListOf("ValeeraDeath"))
        val ValeeraNeedWeapon = CharacterAudioList(arrayListOf("ValeeraNeedWeapon"))
        val ValeeraHandFull = CharacterAudioList(arrayListOf("ValeeraHandFull"))
    }

    override fun play(pitchAdjust: Float) {
        if (RogueModConfig.spireModConfig?.getBool(RogueModConfig.EnableCharacterSFX.key) == true) {
            sfxList.forEach {
                it.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0f + pitchAdjust, 0.0f)
            }
        }
    }
}

class CardAudioList(paths: ArrayList<String>) : AudioList(paths) {
    override fun play(pitchAdjust: Float) {
        if (RogueModConfig.spireModConfig?.getBool(RogueModConfig.EnableCardSFX.key) == true && AbstractDungeon.player != null) {
            sfxList.forEach {
                it.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0f + pitchAdjust, 0.0f)
            }
        }
    }

    companion object {
        // why enum didn't work?? ps:java.lang.NoClassDefFoundError: kotlin/enums/EnumEntriesKt
        val TessGreymanePlay = CardAudioList(arrayListOf("TessGreymaneBgm", "TessGreymaneSound"))
        val TessGreymaneEffect = CardAudioList(arrayListOf("TessGreymaneEffect"))
        val ValeeraTheHollowPlay =
            CardAudioList(arrayListOf("ValeeraTheHollowSound", "ValeeraTheHollowBgm1", "ValeeraTheHollowBgm2"))
        val ShadowcrafterScabbsPlay =
            CardAudioList(arrayListOf("ShadowcrafterScabbsSound", "ShadowcrafterScabbsBgm"))
        val SonyaWaterdancerPlay =
            CardAudioList(arrayListOf("SonyaWaterdancerSound", "SonyaWaterdancerBgm"))
        val SonyaWaterdancerEffect =
            CardAudioList(arrayListOf("SonyaWaterdancerEffect"))
        val SonyaShadowdancerPlay =
            CardAudioList(arrayListOf("SonyaShadowdancerSound", "SonyaShadowdancerBgm"))
        val SonyaShadowdancerEffect =
            CardAudioList(arrayListOf("SonyaShadowdancerEffect"))
        val SherazinCorpseFlowerPlay =
            CardAudioList(arrayListOf("SherazinCorpseFlowerPlay"))
        val SherazinCorpseFlowerEffect =
            CardAudioList(arrayListOf("SherazinCorpseFlowerEffect"))
        val VelarokTheDeceiverEffect =
            CardAudioList(arrayListOf("VelarokTheDeceiverEffect", "VelarokTheDeceiverBgm"))
        val VelarokTheDeceiverAttack =
            CardAudioList(arrayListOf("VelarokTheDeceiverAttackBgm", "VelarokTheDeceiverAttackSound"))
        val VelarokWindbladeAttack =
            CardAudioList(arrayListOf("VelarokWindbladeAttack"))
        val EdwinVanCleefAttack =
            CardAudioList(arrayListOf("EdwinVanCleefAttack"))
        val MaestraMaskMerchantPlay =
            CardAudioList(arrayListOf("MaestraMaskMerchantSound", "MaestraMaskMerchantBgm1", "MaestraMaskMerchantBgm2"))
        val MaestraMaskMerchantEffect =
            CardAudioList(arrayListOf("MaestraMaskMerchantBgm2"))
        val MyraRotspringPlay =
            CardAudioList(arrayListOf("MyraRotspringSound", "MyraRotspringBgm"))
    }
}