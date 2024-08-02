package rogue.cards.attack

import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect
import rogue.action.EmptyAction
import rogue.cards.AbstractRogueCard
import utils.attackWithWeapon
import utils.isWeaponEquipped

class FanOfKnives :
    AbstractRogueCard(
        name = FanOfKnives::class.simpleName.toString(),
        cost = 1,
        type = CardType.ATTACK,
        rarity = CardRarity.COMMON,
        target = CardTarget.ALL_ENEMY
    ) {
    init {
        setMagicNumber(4)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        return (p ?: AbstractDungeon.player).isWeaponEquipped()
    }

    override fun upgrade() {
        useUpgrade {
            upgradeMagicNumber(2)
        }
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        addToTop(VFXAction(DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0f))
        repeat(magicNumber) {
            addToBot(EmptyAction {
                p?.attackWithWeapon(damage = 3)
            })
        }
    }
}