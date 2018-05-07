package alexanders.mods.aoa.render

import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.IRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.entity.Entity
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer
import de.ellpeck.rockbottom.api.util.reg.ResourceName
import de.ellpeck.rockbottom.api.world.IWorld


class PearlRenderer<T : Entity>(val texture: ResourceName) : IEntityRenderer<T> {
    override fun render(game: IGameInstance, manager: IAssetManager, g: IRenderer, world: IWorld, entity: T, x: Float, y: Float, light: Int) {
        manager.getTexture(texture).draw(x, y)
    }
}
