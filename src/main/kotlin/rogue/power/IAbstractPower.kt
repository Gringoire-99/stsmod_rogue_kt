package rogue.power

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower
import utils.logger
import utils.makeId
import utils.modId

abstract class IAbstractPower(
    powerName: String,
    amount: Int = 1,
    type: PowerType = PowerType.BUFF,
    owner: AbstractCreature = AbstractDungeon.player,
    imgPath: String? = null,
    afterInit: () -> Unit = {
    }
) : AbstractPower() {
    var powerString: PowerStrings

    init {
        this.ID = powerName.makeId()
        powerString = CardCrawlGame.languagePack.getPowerStrings(ID)
        this.amount = amount
        DESCRIPTIONS = powerString.DESCRIPTIONS
        this.owner = owner
        this.type = type
        val imgPath48 = if (imgPath == null) "$modId/powers/${powerName}_32.png" else "${imgPath}_32.png"
        val imgPath128 = if (imgPath == null) "$modId/powers/${powerName}_84.png" else "${imgPath}_84.png"
        this.region48 = TextureAtlas.AtlasRegion(ImageMaster.loadImage(imgPath48), 0, 0, 32, 32)
        this.region128 = TextureAtlas.AtlasRegion(ImageMaster.loadImage(imgPath128), 0, 0, 84, 84)
        afterInit()
        logger.info("创建能力$name")
        updateDescription()
    }

    override fun updateDescription() {
        this.description = powerString.DESCRIPTIONS[0]
        name = powerString.NAME
    }


}