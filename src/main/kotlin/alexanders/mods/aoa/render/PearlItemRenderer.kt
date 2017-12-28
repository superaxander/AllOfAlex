package alexanders.mods.aoa.render

import alexanders.mods.aoa.init.Resources.cooldownResource
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.IGraphics
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.item.Item
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer
import de.ellpeck.rockbottom.api.util.reg.IResourceName


class PearlItemRenderer(resourceName: IResourceName) : DefaultItemRenderer<Item>(resourceName) {
    override fun render(game: IGameInstance, manager: IAssetManager, g: IGraphics, item: Item, instance: ItemInstance, x: Float, y: Float, scale: Float, filter: Int) {
        super.render(game, manager, g, item, instance, x, y, scale, filter)
        if (instance.amount == -1)
            return

        if (instance.additionalData != null && instance.additionalData.getInt("cooldown") > 0) {
            val image = manager.getTexture(cooldownResource)
            image.draw(x, y, scale, (scale / 60 * instance.additionalData.getInt("cooldown")), filter)
        }
    }
}
