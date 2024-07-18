package rogue.characters

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType

class RogueEnum {
    companion object {
        @SpireEnum
        @JvmStatic
        lateinit var HS_ROGUE_CLASS: PlayerClass

        @SpireEnum(name = "HS_Rogue_Color")
        @JvmStatic
        lateinit var HS_ROGUE_CARD_COLOR: CardColor

        @SpireEnum(name = "HS_Rogue_Color")
        @JvmStatic
        lateinit var HS_ROGUE_LIBRARY_CARD_COLOR: LibraryType
    }
}