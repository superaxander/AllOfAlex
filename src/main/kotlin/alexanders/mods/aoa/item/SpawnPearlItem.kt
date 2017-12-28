package alexanders.mods.aoa.item

import alexanders.mods.aoa.init.Resources.spawnPearlDescResource
import alexanders.mods.aoa.init.Resources.spawnPearlResource
import alexanders.mods.aoa.net.CooldownUpdatePacket
import alexanders.mods.aoa.net.EntityPositionUpdatePacket
import alexanders.mods.aoa.render.PearlItemRenderer
import alexanders.mods.aoa.render.TeleportationParticle
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.data.set.DataSet
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.ItemBasic
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.IItemRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class SpawnPearlItem() : ItemBasic(spawnPearlResource), Useable {
    val renderer = PearlItemRenderer(spawnPearlResource)
    override fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer) {
        if (itemInstance.additionalData == null) {
            itemInstance.additionalData = DataSet()
            itemInstance.additionalData.addInt("cooldown", 0)
        }
        val cooldown = itemInstance.additionalData.getInt("cooldown")
        if (cooldown <= 0) {
            player.setPos(player.world.spawnX + .5, player.world.getLowestAirUpwards(TileLayer.MAIN, player.world.spawnX, 0) + .5)
            if (RockBottomAPI.getNet().isServer)
                RockBottomAPI.getNet().sendToAllPlayers(player.world, EntityPositionUpdatePacket(player.uniqueId, player.x, player.y))
            for (i in 0..20) RockBottomAPI.getGame().particleManager.addParticle(TeleportationParticle(world = player.world, x = player.x, y = player.y, maxLife = 60))
            itemInstance.additionalData.addInt("cooldown", 60)
            player.sendPacket(CooldownUpdatePacket(60))
        }
    }

    override fun onInteractWith(world: IWorld?, x: Int, y: Int, layer: TileLayer, mouseX: Double, mouseY: Double, player: AbstractEntityPlayer, instance: ItemInstance): Boolean {
        return super<Useable>.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance)
    }

    override fun describeItem(manager: IAssetManager, instance: ItemInstance, desc: MutableList<String>, isAdvanced: Boolean) {
        super.describeItem(manager, instance, desc, isAdvanced)
        desc.add(manager.localize(spawnPearlDescResource))
    }

    override fun getRenderer(): IItemRenderer<*> {
        return renderer
    }

    override fun getMaxAmount(): Int {
        return 1
    }
}

