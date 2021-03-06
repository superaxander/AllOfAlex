package alexanders.mods.aoa.entity

import alexanders.mods.aoa.AllOfAlex.createRes
import alexanders.mods.aoa.PLAYER_UUID
import alexanders.mods.aoa.init.Resources
import alexanders.mods.aoa.render.PearlParticle
import alexanders.mods.aoa.render.PearlRenderer
import alexanders.mods.aoa.render.TeleportationParticle
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer
import java.util.*


class MiningPearlEntity(world: IWorld, player: UUID? = null, mouseDirection: FloatArray = floatArrayOf(0f, 0f)) : Entity(world) {
    val TILES_BROKEN = createRes("tilesBroken")

    val renderer = PearlRenderer<BouncyPearlEntity>(Resources.miningPearlResource)

    override fun getRenderer(): IEntityRenderer<*> {
        return renderer
    }

    init {
        if (this.additionalData == null) {
            this.additionalData = ModBasedDataSet()
            if (player != null)
                this.additionalData.addUniqueId(PLAYER_UUID, player)
            this.additionalData.addInt(TILES_BROKEN, 0)
        }

        this.motionX = 0.5 * mouseDirection[0]
        this.motionY = -0.5 * mouseDirection[1]
        if (player != null) {
            val ePlayer = world.getEntity(player)
            this.setPos(ePlayer.x, ePlayer.y + 1.5)
        } else if (this.additionalData.getUniqueId(PLAYER_UUID) != null) {
            val ePlayer = world.getEntity(this.additionalData.getUniqueId(PLAYER_UUID))
            this.setPos(ePlayer.x, ePlayer.y)
        }
        //println("$x , $y")
    }

    constructor(world2: IWorld) : this(world = world2)

    override fun update(game: IGameInstance) {
        val recoveryX = motionX
        val recoveryY = motionY
        super.update(game)
        if (!game.isDedicatedServer)
            game.particleManager.addParticle(TeleportationParticle(world = game.world, x = x, y = y, motionX = motionX / 2 * PearlParticle.randomSignedDouble(), maxLife = 10))
        if (collidedVert || collidedHor) {
            motionX = recoveryX
            motionY = recoveryY
            applyMotion()
            this.onGround = false
            val tilesBroken = this.additionalData.getInt(TILES_BROKEN)
            val uuid = this.additionalData.getUniqueId(PLAYER_UUID)
            //println("$x , $y")
            if (uuid != null) {
                val player = world.getEntity(uuid)
                if (player is AbstractEntityPlayer && (RockBottomAPI.getNet().isServer || !RockBottomAPI.getNet().isActive) && breakAround(player))
                    this.additionalData.addInt(TILES_BROKEN, tilesBroken + 1)
            }
            if (this.motionX == .0 && motionY == .0 || tilesBroken >= 6)
                this.kill()
        }
    }

    private fun breakAround(player: AbstractEntityPlayer): Boolean {
        var out = false
        for (i in -1..1) {
            for (z in -1..1) {
                val tile = world.getState(TileLayer.MAIN, Math.round(x).toInt() + i, Math.round(y).toInt() + z).tile
                if (!tile.isAir) {
                    tile.doBreak(world, Math.round(x).toInt() + i, Math.round(y).toInt() + z, TileLayer.MAIN, player, true, true)
                    out = true
                }
            }
        }
        return out
    }


}
