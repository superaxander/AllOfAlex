package alexanders.mods.aoa.render

import alexanders.mods.aoa.init.Resources.teleportationParticleResource
import de.ellpeck.rockbottom.api.util.Util
import de.ellpeck.rockbottom.api.world.IWorld


class TeleportationParticle(world: IWorld, x: Double, y: Double, motionX: Double = randomSignedDouble() * .3, motionY: Double = (Util.RANDOM.nextDouble()) * .3,
                            maxLife: Int = 60) : PearlParticle(teleportationParticleResource, world, x, y, motionX, motionY, maxLife) {

    constructor(world: IWorld) : this(world = world, x = .0, y = .0, maxLife = 0) // Is this needed
}
