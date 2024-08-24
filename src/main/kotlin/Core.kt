import basemod.AutoAdd
import basemod.BaseMod
import basemod.interfaces.*
import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.google.gson.Gson
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.*
import com.megacrit.cardcrawl.unlock.UnlockTracker
import rogue.cards.variables.*
import rogue.characters.Rogue
import rogue.characters.RogueEnum
import rogue.modconfig.RogueModConfig
import rogue.relics.IAbstractRelic
import utils.*
import java.nio.charset.StandardCharsets


@SpireInitializer
class Core : EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber,
    EditRelicsSubscriber, PostInitializeSubscriber {
    init {
        BaseMod.subscribe(this)
        BaseMod.addColor(
            RogueEnum.HS_ROGUE_CARD_COLOR,
            Rogue_Color,
            Rogue_Color,
            Rogue_Color,
            Rogue_Color,
            Rogue_Color,
            Rogue_Color,
            Rogue_Color,
            BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENERGY_ORB, BG_ATTACK_1024,
            BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB
        )
        RogueModConfig.loadConfig()
    }

    companion object {
        // 人物选择界面按钮的图片
        const val CHAR_BUTTON: String = IMG_Select_Btn

        // 人物选择界面的图像
        const val CHAR_PORTRAIT: String = IMG_Valeera

        // 卡牌背景（小）
        const val BG_ATTACK_512: String = IMG_Attack_bg_sm
        const val BG_SKILL_512: String = IMG_Skill_bg_sm
        const val BG_POWER_512: String = IMG_Power_bg_sm

        // 左上角的能量图标
        const val ENERGY_ORB: String = IMG_orb_cost

        // 卡牌背景（大）
        const val BG_ATTACK_1024: String = IMG_Attack_bg_lg
        const val BG_SKILL_1024: String = IMG_Skill_bg_lg
        const val BG_POWER_1024: String = IMG_Power_bg_lg

        // 大能量（用于大图展示）
        const val BIG_ORB: String = IMG_orb_lg

        // 小能量（用于描述等）
        const val SMALL_ORB: String = IMG_orb_sm

        @JvmStatic
        fun initialize() {
            Core()
        }
    }

    override fun receiveEditCards() {
        AutoAdd(modId).packageFilter("rogue.cards").setDefaultSeen(true).cards()
        BaseMod.addDynamicVariable(WeaponDamageVariable())
        BaseMod.addDynamicVariable(WeaponDurabilityVariable())
        BaseMod.addDynamicVariable(ExpVariable())
        BaseMod.addDynamicVariable(MaxExpVariable())
        BaseMod.addDynamicVariable(TendrilCountVariable())
    }


    override fun receiveEditStrings() {
        val lang: String = getLangSupport()
        val prefix = "${modId}/localization/$lang/"
        BaseMod.loadCustomStringsFile(CardStrings::class.java, "${prefix}cards.json")
        BaseMod.loadCustomStringsFile(CharacterStrings::class.java, "${prefix}character.json")
        BaseMod.loadCustomStringsFile(RelicStrings::class.java, "${prefix}relics.json")
        BaseMod.loadCustomStringsFile(PowerStrings::class.java, "${prefix}powers.json")
        BaseMod.loadCustomStringsFile(UIStrings::class.java, "${prefix}ui.json")
    }

    private fun getLangSupport(): String {
        return when (Settings.language) {
            Settings.GameLanguage.ZHS -> "ZHS"
            Settings.GameLanguage.ENG -> "ENG"
            else -> {
                "ENG"
            }
        }
    }

    override fun receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(
            Rogue(),
            CHAR_BUTTON,
            CHAR_PORTRAIT,
            RogueEnum.HS_ROGUE_CLASS
        )
    }

    override fun receiveEditKeywords() {
        val gson = Gson()
        val lang = getLangSupport()

        val json = Gdx.files.internal("$modId/localization/$lang/keywords.json")
            .readString(StandardCharsets.UTF_8.toString())
        val keywords = gson.fromJson(
            json,
            Array<Keyword>::class.java
        )
        if (keywords != null) {
            for (keyword in keywords) {
                // 这个id要全小写！！
                logger.info("添加关键词" + keyword.NAMES[0])
                BaseMod.addKeyword(modId.lowercase(), keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION)
            }
        }
    }

    override fun receiveEditRelics() {
        AutoAdd(modId)
            .packageFilter("rogue.relics")
            .any(IAbstractRelic::class.java) { _: AutoAdd.Info?, relic: IAbstractRelic ->
                BaseMod.addRelicToCustomPool(relic, RogueEnum.HS_ROGUE_CARD_COLOR)
                UnlockTracker.markRelicAsSeen(relic.relicId)
            }
    }

    override fun receivePostInitialize() {
        RogueModConfig.initModMenu()
    }

}