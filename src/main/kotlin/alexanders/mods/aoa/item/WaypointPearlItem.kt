package alexanders.mods.aoa.item

import alexanders.mods.aoa.AllOfAlex.createRes
import alexanders.mods.aoa.COOLDOWN
import alexanders.mods.aoa.init.Resources.*
import alexanders.mods.aoa.net.CooldownUpdatePacket
import alexanders.mods.aoa.net.EntityPositionUpdatePacket
import alexanders.mods.aoa.render.CooldownableRenderer
import alexanders.mods.aoa.render.TeleportationParticle
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.assets.IAssetManager
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.ItemBasic
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.render.item.IItemRenderer
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


class WaypointPearlItem : ItemBasic(waypointPearlResource), Useable, ICooldownable {
    val WAYPOINT_X = createRes("waypoint_x")
    val WAYPOINT_Y = createRes("waypoint_y")
    override fun getMaxCooldown() = 60f
    val renderer = CooldownableRenderer<WaypointPearlItem>(waypointPearlResource)
    override fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer, shiftPressed: Boolean) {
        if (itemInstance.additionalData == null) {
            itemInstance.additionalData = ModBasedDataSet()
            itemInstance.additionalData.addInt(COOLDOWN, 0)
            itemInstance.additionalData.addDouble(WAYPOINT_X, player.world.spawnX + .5)
            itemInstance.additionalData.addDouble(WAYPOINT_Y, player.world.getExpectedSurfaceHeight(TileLayer.MAIN, player.world.spawnX) + .5)
        }
        if (shiftPressed) {
            itemInstance.additionalData.addDouble(WAYPOINT_X, player.x)
            itemInstance.additionalData.addDouble(WAYPOINT_Y, player.y)
        } else {
            val cooldown = itemInstance.additionalData.getInt(COOLDOWN)
            if (cooldown <= 0) {
                player.setPos(itemInstance.additionalData.getDouble(WAYPOINT_X), itemInstance.additionalData.getDouble(WAYPOINT_Y))
                if (RockBottomAPI.getNet().isServer)
                    RockBottomAPI.getNet().sendToAllPlayers(player.world, EntityPositionUpdatePacket(player.uniqueId, player.x, player.y))
                for (i in 0..20) RockBottomAPI.getGame().particleManager.addParticle(TeleportationParticle(world = player.world, x = player.x, y = player.y, maxLife = 60))
                itemInstance.additionalData.addInt(COOLDOWN, 60)
                //if (itemInstance.removeAmount(1).amount <= 0)
                //    player.inv[player.selectedSlot] = null
                player.sendPacket(CooldownUpdatePacket(60, player.selectedSlot))
            }
        }
    }

    override fun describeItem(manager: IAssetManager, instance: ItemInstance, desc: MutableList<String>, isAdvanced: Boolean) {
        super.describeItem(manager, instance, desc, isAdvanced)
        desc.add(manager.localize(waypointPearlDescResource))
        if (isAdvanced) {
            desc.add(manager.localize(waypointPearlUsageDescResource))
            if (instance.additionalData == null) {
                instance.additionalData = ModBasedDataSet()
                instance.additionalData.addInt(COOLDOWN, 0)
                instance.additionalData.addDouble(WAYPOINT_X, RockBottomAPI.getGame().world.spawnX + .5)
                instance.additionalData.addDouble(WAYPOINT_Y, RockBottomAPI.getGame().world.getExpectedSurfaceHeight(TileLayer.MAIN, RockBottomAPI.getGame().world.spawnX) + .5)
            }
            desc.add("${manager.localize(setToDescResource)}${instance.additionalData.getDouble(WAYPOINT_X)}, ${instance.additionalData.getDouble(WAYPOINT_Y)}")
        } else {
            desc.add(manager.localize(moreInfoDescResource))
        }
    }

    override fun onInteractWith(world: IWorld?, x: Int, y: Int, layer: TileLayer, mouseX: Double, mouseY: Double, player: AbstractEntityPlayer, instance: ItemInstance): Boolean {
        return super<Useable>.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance)
    }

    override fun getRenderer(): IItemRenderer<*> {
        return renderer
    }

    override fun getMaxAmount(): Int {
        return 1
    }
}

