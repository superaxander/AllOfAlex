package alexanders.mods.aoa.entity

import alexanders.mods.aoa.AllOfAlex.createRes
import alexanders.mods.aoa.PLAYER_UUID
import alexanders.mods.aoa.init.Resources
import alexanders.mods.aoa.init.Tiles.phantomTile
import alexanders.mods.aoa.render.PearlParticle
import alexanders.mods.aoa.render.PearlRenderer
import alexanders.mods.aoa.render.TeleportationParticle
import alexanders.mods.aoa.tile.PhantomTile
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer
import java.util.*


class BridgingPearlEntity(world: IWorld, player: UUID? = null, mouseDirection: FloatArray = floatArrayOf(0f, 0f)) : Entity(world) {
    val TILES_PLACED = createRes("tilesPlaced")
    val renderer = PearlRenderer<BouncyPearlEntity>(Resources.bridgingPearlResource)

    override fun getRenderer(): IEntityRenderer<*> {
        return renderer
    }

    init {
        if (this.additionalData == null) {
            this.additionalData = ModBasedDataSet()
            if (player != null)
                this.additionalData.addUniqueId(PLAYER_UUID, player)
            this.additionalData.addInt(TILES_PLACED, 0)
        }

        this.motionX = mouseDirection[0].toDouble()
        this.motionY = -mouseDirection[0].toDouble()
        if (player != null) {
            val ePlayer = world.getEntity(player)
            this.setPos(ePlayer.x, ePlayer.y - .5f)
        } else if (this.additionalData.getUniqueId(PLAYER_UUID) != null) {
            val ePlayer = world.getEntity(this.additionalData.getUniqueId(PLAYER_UUID))
            this.setPos(ePlayer.x, ePlayer.y - .5f)
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
            val tile = world.getState(Math.round(x).toInt(), Math.round(y).toInt()).tile //TileLayer.MAIN, )
            if (tile is PhantomTile) {
                collidedHor = false
                collidedVert = false
                onGround = false
                motionX = recoveryX
                motionY = recoveryY
                applyMotion()
            }
        }
        val tilesPlaced = this.additionalData.getInt(TILES_PLACED)
        val uuid = this.additionalData.getUniqueId(PLAYER_UUID)
        //println("$x , $y")
        if (uuid != null) {
            val player = world.getEntity(uuid)
            if (player is AbstractEntityPlayer && (RockBottomAPI.getNet().isServer || !RockBottomAPI.getNet().isActive)) {
                placePhantomTile()
                this.additionalData.addInt(TILES_PLACED, tilesPlaced + 1)
            }
        }
        if (this.onGround || tilesPlaced >= 12)
            this.kill()

    }

    private fun placePhantomTile() {
        //println("Placing at $x, $y");
        val tile = world.getState(TileLayer.MAIN, Math.round(x).toInt(), Math.round(y).toInt()).tile
        //println(tile.isAir)
        if (tile.isAir)
            world.setState(TileLayer.MAIN, Math.round(x).toInt(), Math.round(y).toInt(), phantomTile.defState)
        //tile.doPlace(world, Math.round(x).toInt(), Math.round(y).toInt(), TileLayer.MAIN, ItemInstance(LOP.instance.phantomTile), player)
    }
}
