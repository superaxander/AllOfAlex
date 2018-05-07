package alexanders.mods.aoa.render

import alexanders.mods.aoa.init.Resources.bloodParticleResource
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.IRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.particle.Particle
import de.ellpeck.rockbottom.api.util.Util
import de.ellpeck.rockbottom.api.util.reg.ResourceName
import de.ellpeck.rockbottom.api.world.IWorld


open class PearlParticle(val name: ResourceName, world: IWorld, x: Double, y: Double, motionX: Double = randomSignedDouble() * .3,
                         motionY: Double = (Util.RANDOM.nextDouble()) * .3, maxLife: Int = 60) : Particle(world, x, y, motionX, motionY, maxLife) {
    companion object {
        fun randomSignedDouble(): Double = if (Util.RANDOM.nextBoolean()) Util.RANDOM.nextDouble() else -Util.RANDOM.nextDouble()
    }

    constructor(world: IWorld) : this(bloodParticleResource, world = world, x = .0, y = .0, maxLife = 0) // Is this needed

    override fun render(game: IGameInstance, manager: IAssetManager, g: IRenderer, x: Float, y: Float, filter: Int) {
        super.render(game, manager, g, x, y, filter)
        manager.getTexture(name).draw(x, y, .25f, .25f, filter)
    }
}
