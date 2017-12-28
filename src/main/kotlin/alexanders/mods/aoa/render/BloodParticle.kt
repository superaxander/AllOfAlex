package alexanders.mods.aoa.render

import alexanders.mods.aoa.init.Resources.bloodParticleResource
import de.ellpeck.rockbottom.api.util.Util
import de.ellpeck.rockbottom.api.world.IWorld


class BloodParticle(world: IWorld, x: Double, y: Double, motionX: Double = randomSignedDouble() * .3, motionY: Double = (Util.RANDOM.nextDouble()) * .3, maxLife: Int = 60) :
        PearlParticle(bloodParticleResource, world, x, y, motionX, motionY, maxLife) {

    constructor(world: IWorld) : this(world = world, x = .0, y = .0, maxLife = 0) // Is this needed
}
