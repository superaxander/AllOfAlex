package alexanders.mods.aoa.render

import alexanders.mods.aoa.init.Resources.cooldownResource
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.IRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.Item
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer
import de.ellpeck.rockbottom.api.util.reg.IResourceName


class PearlItemRenderer(resourceName: IResourceName) : DefaultItemRenderer<Item>(resourceName) {
    override fun render(game: IGameInstance, manager: IAssetManager, g: IRenderer, item: Item, instance: ItemInstance, x: Float, y: Float, scale: Float, filter: Int) {
        super.render(game, manager, g, item, instance, x, y, scale, filter)
        if (instance.amount == -1)
            return

        if (instance.additionalData != null && instance.additionalData.getInt("cooldown") > 0) {
            val image = manager.getTexture(cooldownResource)
            image.draw(x, y, scale, (scale / 60 * instance.additionalData.getInt("cooldown")), filter)
        }
    }

    override fun renderHolding(game: IGameInstance, manager: IAssetManager, g: IRenderer, item: Item, instance: ItemInstance, player: AbstractEntityPlayer, x: Float,
                               y: Float, rotation: Float, scale: Float, filter: Int, mirrored: Boolean) {
        g.translate(x, y)
        g.rotate(rotation)

        if (mirrored) {
            g.mirror(true, false)
        }

        this.render(game, manager, g, item, instance.copy().setAmount(-1), 0f, 0f, scale * 0.5f, filter)

        if (mirrored) {
            g.mirror(true, false)
        }

        g.rotate(-rotation)
        g.translate(-x, -y)
    }
}
