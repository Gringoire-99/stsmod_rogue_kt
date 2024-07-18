package rogue.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.helpers.ImageMaster
import utils.logger
import utils.makeId
import utils.modId

fun getImgPath(name: String) = "$modId/relics/$name.png"

abstract class IAbstractRelic(
    name: String,
    tier: RelicTier = RelicTier.COMMON,
    sfx: LandingSound = LandingSound.CLINK
) :
    CustomRelic(name.makeId(), ImageMaster.loadImage(getImgPath(name)), tier, sfx) {
    init {
        val relicString = CardCrawlGame.languagePack.getRelicStrings(relicId)
        DESCRIPTIONS[0] = relicString.DESCRIPTIONS[0]
        flavorText = relicString.FLAVOR
        logger.info("创建遗物$name")
    }

    override fun getUpdatedDescription(): String {
        return this.DESCRIPTIONS[0]
    }

}