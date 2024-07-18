package rogue.characters

import basemod.abstracts.CustomPlayer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.cutscenes.CutscenePanel
import com.megacrit.cardcrawl.events.city.Vampires
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.localization.CharacterStrings
import com.megacrit.cardcrawl.screens.CharSelectInfo
import rogue.cards.attack.Backstab
import rogue.cards.attack.Strike
import rogue.cards.skill.Conceal
import rogue.cards.skill.DeadlyPoison
import rogue.cards.skill.Defend
import rogue.relics.RogueBaseAbility
import utils.*

class Rogue() : CustomPlayer(
    name, playerClass, orbTextures, orbVfxPath, layerSpeed, null, null
) {
    companion object {
        val name = CardCrawlGame.playerName
        val playerClass = RogueEnum.HS_ROGUE_CLASS
        val orbTextures = arrayOf(
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_6,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
            IMG_orb_layer_1,
        )
        val orbVfxPath = IMG_orb_vfx
        val layerSpeed = arrayOf(-40.0f, -32.0f, 20.0f, -20.0f, 0.0f, -10.0f, -8.0f, 5.0f, -5.0f, 0.0f).toFloatArray()
        val characterStrings: CharacterStrings = CardCrawlGame.languagePack.getCharacterString(Rogue::class.makeId())
        val MY_CHARACTER_SHOULDER_2 = IMG_Valeera_shoulder
        val MY_CHARACTER_SHOULDER_1 = IMG_Valeera_shoulder
        val CORPSE_IMAGE = IMG_Valeera_corpse
    }

    init {

        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0f * Settings.scale)
        this.dialogY = (this.drawY + 150.0f * Settings.scale)


        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
            IMG_Valeera_p,  // 人物图片
            MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1, Rogue.CORPSE_IMAGE,  // 人物死亡图像
            this.loadout, 0.0f, 0.0f, 200.0f, 220.0f,  // 人物碰撞箱大小，越大的人物模型这个越大
            EnergyManager(3) // 初始每回合的能量
        )
    }

    override fun getStartingDeck(): ArrayList<String> {
        val list = ArrayList<String>()
        list.apply {
            repeat(4) {
                add(Strike::class.makeId())
            }
            repeat(5) {
                add(Defend::class.makeId())
            }
            add(DeadlyPoison::class.makeId())
            add(Backstab::class.makeId())
            add(Conceal::class.makeId())
        }

        return list
    }

    override fun getStartingRelics(): ArrayList<String> {
        return arrayListOf(RogueBaseAbility::class.makeId())
    }

    override fun getLoadout(): CharSelectInfo {
        return CharSelectInfo(
            characterStrings.NAMES[0], // 人物名字
            characterStrings.TEXT[0], // 人物介绍
            70, // 当前血量
            70, // 最大血量
            0, // 初始充能球栏位
            99, // 初始携带金币
            5, // 每回合抽牌数量
            this, // 别动
            this.startingRelics, // 初始遗物
            this.getStartingDeck(), // 初始卡组
            false // 别动
        )
    }

    override fun getTitle(p0: PlayerClass?): String {
        return characterStrings.NAMES[0]
    }

    override fun getCardColor(): AbstractCard.CardColor {
        return RogueEnum.HS_ROGUE_CARD_COLOR
    }

    override fun getCardRenderColor(): Color {
        return Rogue_Color
    }

    override fun getStartCardForEvent(): AbstractCard {
        return Strike()
    }

    override fun getCardTrailColor(): Color {
        return Rogue_Color
    }

    override fun getAscensionMaxHPLoss(): Int {
        return 5
    }

    override fun getEnergyNumFont(): BitmapFont {
        return FontHelper.energyNumFontBlue
    }

    override fun doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false)
    }

    override fun getCustomModeCharacterButtonSoundKey(): String {
        return "ATTACK_HEAVY"
    }

    override fun getCutscenePanels(): MutableList<CutscenePanel> {
        val panels = arrayListOf(
            CutscenePanel(IMG_Victory_1, "ATTACK_MAGIC_FAST_1"),
            CutscenePanel(IMG_Victory_2),
            CutscenePanel(IMG_Victory_3),
        )

        return panels
    }

    override fun getLocalizedCharacterName(): String {
        return characterStrings.NAMES[0]
    }

    override fun newInstance(): AbstractPlayer {
        return Rogue()
    }

    override fun getSpireHeartText(): String {
        return characterStrings.TEXT[1]
    }

    override fun getSlashAttackColor(): Color {
        return Rogue_Color
    }

    override fun getSpireHeartSlashEffect(): Array<AbstractGameAction.AttackEffect> {
        return arrayOf(
            AttackEffect.SLASH_HEAVY,
            AttackEffect.FIRE,
            AttackEffect.SLASH_DIAGONAL,
            AttackEffect.SLASH_HEAVY,
            AttackEffect.FIRE,
            AttackEffect.SLASH_DIAGONAL
        )
    }

    override fun getVampireText(): String {
        return Vampires.DESCRIPTIONS[0]
    }

}