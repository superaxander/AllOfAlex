package alexanders.mods.aoa.entity

import alexanders.mods.aoa.AllOfAlex.createRes
import alexanders.mods.aoa.PLAYER_UUID
import alexanders.mods.aoa.init.Resources.bouncyPearlResource
import alexanders.mods.aoa.net.EntityPositionUpdatePacket
import alexanders.mods.aoa.render.PearlParticle
import alexanders.mods.aoa.render.PearlRenderer
import alexanders.mods.aoa.render.TeleportationParticle
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import java.util.*

class BouncyPearlEntity(world: IWorld, player: UUID? = null, mouseDirection: FloatArray = floatArrayOf(0f, 0f)) : Entity(world) {
    val BOUNCES = createRes("BOUNCES")

    val renderer = PearlRenderer<BouncyPearlEntity>(bouncyPearlResource)

    override fun getRenderer(): IEntityRenderer<*> {
        return renderer
    }

    init {

        if (this.additionalData == null) {
            this.additionalData = ModBasedDataSet()
            if (player != null)
                this.additionalData.addUniqueId(PLAYER_UUID, player);
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
        applyMotion()
        if (!game.isDedicatedServer)
            game.particleManager.addParticle(TeleportationParticle(world = game.world, x = x, y = y, motionX = motionX / 2 * PearlParticle.randomSignedDouble(), maxLife = 10))
        move()
        if (collidedVert || collidedHor) {

            if (this.additionalData.getInt(BOUNCES) >= 3) {
                val uuid = this.additionalData.getUniqueId(PLAYER_UUID)
                //println("$x , $y")
                if (uuid != null) {
                    val e = world.getEntity(uuid)
                    if (e != null) {
                        e.setPos(this.x, this.y + 1.2f)
                        if (RockBottomAPI.getNet().isServer) {
                            RockBottomAPI.getNet().sendToAllPlayers(world, EntityPositionUpdatePacket(uuid, x, y + 1.2f))
                        }
                        if (!game.isDedicatedServer)
                            for (i in 0..20) game.particleManager.addParticle(TeleportationParticle(world = game.world, x = x, y = y, maxLife = 60))

                    }
                }
                this.kill()
            } else {
                if (!this.additionalData.hasKey(BOUNCES.toString()))
                    this.additionalData.addInt(BOUNCES, 1)
                this.additionalData.addInt(BOUNCES, this.additionalData.getInt(BOUNCES) + 1)
                if (this.collidedHor)
                    this.motionX = -(this.motionX * .85f)
                if (this.collidedVert)
                    this.motionY = -(this.motionY * .85f)
            }
        }
    }
}
