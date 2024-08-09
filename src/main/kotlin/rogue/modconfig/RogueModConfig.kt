package rogue.modconfig

import basemod.BaseMod
import basemod.ModLabeledToggleButton
import basemod.ModPanel
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.UIStrings
import utils.logger
import utils.makeId
import utils.modId
import java.util.*
import kotlin.math.ceil

const val XSpace = 90f
const val YSpace = 50f
const val XShift = 300f
const val YShift = 800f


class RogueModConfig {
    companion object {
        class Option<T : Any>(val key: String, val value: T)

        private val ToggleOptionID = "ConfigToggleOptions".makeId()
        val EnableCardSFX = Option("EnableCardSFX", false)
        val EnableCharacterSFX = Option("EnableCharacterSFX", false)
        val DiscoverNonModCards = Option("DiscoverNonModCards", false)
        private val toggleOptionList: ArrayList<Option<Boolean>> =
//        The order must match the ui.json file!
            arrayListOf(
                EnableCardSFX,
                EnableCharacterSFX,
                DiscoverNonModCards
            )

        private fun <T : Any> Properties.setProperty(option: Option<T>) {
            setProperty(option.key, option.value.toString())
        }

        private val defaultConfig: Properties = Properties().apply {
            toggleOptionList.forEach {
                setProperty(it)
            }
        }
        var spireModConfig: SpireConfig? = null
        private var toggleUIString: UIStrings? = null
        private var modPanel: ModPanel? = null
        private var itemCount = 1
        private const val COL = 2

        fun initModMenu() {
            toggleUIString = CardCrawlGame.languagePack.getUIString(ToggleOptionID)
            val texts = toggleUIString?.TEXT
            modPanel = ModPanel()
            toggleOptionList.forEachIndexed { index, configToggleOptions ->
                val shift = (index % COL) * 500f
                addToggleBtn(texts?.get(index) ?: "missing label", configToggleOptions.key, shift)
            }

//          👆 don't work if no register badge
            BaseMod.registerModBadge(
                ImageMaster.loadImage("$modId/UI/badge.png"),
                modId,
                "glen",
                "A character from hearthstone", modPanel
            )
        }

        fun loadConfig() {
            spireModConfig = SpireConfig(modId, RogueModConfig::class.makeId(), defaultConfig)
            spireModConfig?.load()
        }

        private fun addToggleBtn(
            label: String,
            key: String,
            additionalShift: Float = 0f
        ) {
            //java.lang.NoSuchMethodError: kotlin.text.StringsKt.toBooleanStrict(java/lang/String) why did this happen??/(ㄒoㄒ)/
            val value = spireModConfig?.getBool(key) ?: toggleOptionList.find { it.key == key }?.value
            ?: throw RuntimeException("$key not exist").apply { logger.error(this) }
            modPanel?.addUIElement(
                ModLabeledToggleButton(
                    label,
                    XShift + XSpace * ((itemCount) % COL) + additionalShift,
                    YShift - YSpace * (ceil(((itemCount).toFloat() / COL))),
                    Settings.CREAM_COLOR,
                    FontHelper.charDescFont,
                    value,
                    modPanel,
                    {}, { b ->
                        spireModConfig?.apply {
                            setBool(key, b.enabled)
                            save()
                        }
                    }
                )
            )
            itemCount++
        }

        fun getBoolConfig(option: Option<Boolean>): Boolean {
            return spireModConfig?.getBool(option.key) == true
        }

    }

}
