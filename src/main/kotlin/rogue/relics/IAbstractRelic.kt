package rogue.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.RelicStrings
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

    private val relicString: RelicStrings = CardCrawlGame.languagePack.getRelicStrings(relicId)

    init {
        DESCRIPTIONS[0] = relicString.DESCRIPTIONS[0]
        flavorText = relicString.FLAVOR
        logger.info("创建遗物$name")
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}