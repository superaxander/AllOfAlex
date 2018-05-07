package alexanders.mods.aoa.item

import alexanders.mods.aoa.COOLDOWN
import alexanders.mods.aoa.entity.BridgingPearlEntity
import alexanders.mods.aoa.init.Resources.bridgingPearlDescResource
import alexanders.mods.aoa.init.Resources.bridgingPearlResource
import alexanders.mods.aoa.net.CooldownUpdatePacket
import alexanders.mods.aoa.render.CooldownableRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.Item
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.IItemRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class BridgingPearlItem : Item(bridgingPearlResource), Useable, ICooldownable {
    override fun getMaxCooldown() = 60f
    val renderer = CooldownableRenderer<BridgingPearlItem>(bridgingPearlResource)
    override fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer) {
        if (itemInstance.additionalData == null) {
            itemInstance.additionalData = ModBasedDataSet()
            itemInstance.additionalData.addInt(COOLDOWN, 0)
        }
        val cooldown = itemInstance.additionalData.getInt(COOLDOWN)
        if (cooldown <= 0) {
            val pearlEntity = BridgingPearlEntity(player.world, player.uniqueId, mouseDirection)
            player.world.addEntity(pearlEntity)
            itemInstance.additionalData.addInt(COOLDOWN, 60)
            if (itemInstance.removeAmount(1).amount <= 0)
                player.inv[player.selectedSlot] = null
            player.sendPacket(CooldownUpdatePacket(60, player.selectedSlot))
        }
    }

    override fun onInteractWith(world: IWorld?, x: Int, y: Int, layer: TileLayer, mouseX: Double, mouseY: Double, player: AbstractEntityPlayer, instance: ItemInstance): Boolean {
        return super<Useable>.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance)
    }

    override fun describeItem(manager: IAssetManager, instance: ItemInstance, desc: MutableList<String>, isAdvanced: Boolean) {
        super.describeItem(manager, instance, desc, isAdvanced)
        desc.add(manager.localize(bridgingPearlDescResource))
    }

    override fun getRenderer(): IItemRenderer<*> {
        return renderer
    }
}
