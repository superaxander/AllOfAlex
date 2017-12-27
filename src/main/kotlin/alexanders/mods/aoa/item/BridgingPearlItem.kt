package alexanders.mods.aoa.item

import alexanders.mods.aoa.entity.BridgingPearlEntity
import alexanders.mods.aoa.init.Resources.BRIDGING_PEARL_DESC_RESOURCE
import alexanders.mods.aoa.init.Resources.BRIDGING_PEARL_RESOURCE
import alexanders.mods.aoa.net.CooldownUpdatePacket
import alexanders.mods.aoa.render.PearlItemRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.data.set.DataSet
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.Item
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.IItemRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class BridgingPearlItem : Item(BRIDGING_PEARL_RESOURCE), Useable {
    val renderer = PearlItemRenderer(BRIDGING_PEARL_RESOURCE)
    override fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer) {
        if (itemInstance.additionalData == null) {
            itemInstance.additionalData = DataSet()
            itemInstance.additionalData.addInt("cooldown", 0)
        }
        val cooldown = itemInstance.additionalData.getInt("cooldown")
        if (cooldown <= 0) {
            val pearlEntity = BridgingPearlEntity(player.world, player.uniqueId, mouseDirection)
            player.world.addEntity(pearlEntity)
            itemInstance.additionalData.addInt("cooldown", 60)
            if (itemInstance.removeAmount(1).amount <= 0)
                player.inv[player.selectedSlot] = null
            player.sendPacket(CooldownUpdatePacket(60))
        }
    }

    override fun onInteractWith(world: IWorld?, x: Int, y: Int, layer: TileLayer, mouseX: Double, mouseY: Double, player: AbstractEntityPlayer, instance: ItemInstance): Boolean {
        return super<Useable>.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance)
    }

    override fun describeItem(manager: IAssetManager, instance: ItemInstance, desc: MutableList<String>, isAdvanced: Boolean) {
        super.describeItem(manager, instance, desc, isAdvanced)
        desc.add(manager.localize(BRIDGING_PEARL_DESC_RESOURCE))
    }

    override fun getRenderer(): IItemRenderer<*> {
        return renderer
    }
}
