package alexanders.mods.aoa.item

import alexanders.mods.aoa.COOLDOWN
import alexanders.mods.aoa.entity.PearlEntity
import alexanders.mods.aoa.init.Resources.pearlDescResource
import alexanders.mods.aoa.init.Resources.pearlResource
import alexanders.mods.aoa.net.CooldownUpdatePacket
import alexanders.mods.aoa.render.CooldownableRenderer
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.ItemBasic
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.IItemRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class PearlItem : ItemBasic(pearlResource), Useable, ICooldownable {

    override fun getMaxCooldown() = 60f
    val renderer = CooldownableRenderer<PearlItem>(pearlResource)
    override fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer) {
        if (itemInstance.additionalData == null) {
            itemInstance.additionalData = ModBasedDataSet()
            itemInstance.additionalData.addInt(COOLDOWN, 0)
        }
        val cooldown = itemInstance.additionalData.getInt(COOLDOWN)
        if (cooldown <= 0) {
            val pearlEntity = PearlEntity(player.world, player.uniqueId, mouseDirection)
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
        desc.add(manager.localize(pearlDescResource))
    }

    override fun getRenderer(): IItemRenderer<*> {
        return renderer
    }
}
