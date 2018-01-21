package alexanders.mods.aoa.item

import alexanders.mods.aoa.init.Keys
import alexanders.mods.aoa.net.ItemUsePacket
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer


interface Useable {
    fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer) {}
    fun use(itemInstance: ItemInstance, mouseDirection: FloatArray, player: AbstractEntityPlayer, shiftPressed: Boolean) {
        use(itemInstance, mouseDirection, player)
    }

    fun onInteractWith(world: IWorld?, x: Int, y: Int, layer: TileLayer, mouseX: Double, mouseY: Double, player: AbstractEntityPlayer, instance: ItemInstance): Boolean {
        val net = RockBottomAPI.getNet()
        val game = RockBottomAPI.getGame()

        if (player.inv[player.selectedSlot] != null) {

            if (net.isClient) {
                net.sendToServer(ItemUsePacket(angle(), player.uniqueId, Keys.KEY_SET_WAYPOINT.isDown))
            } else {
                ItemUsePacket(angle(), player.uniqueId, Keys.KEY_SET_WAYPOINT.isDown).handle(game, null)
            }
        }
        return false
    }

    companion object {
        fun angle(): FloatArray {
            val game = RockBottomAPI.getGame()
            val w = game.width
            val h = game.height
            val i = game.input
            val radians = Math.atan2(i.mouseY - (h / 2.0), i.mouseX - (w / 2.0))
            return floatArrayOf(Math.cos(radians).toFloat(), Math.sin(radians).toFloat())
        }
    }
}
