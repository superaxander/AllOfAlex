package alexanders.mods.aoa.item

import alexanders.mods.aoa.init.Keys
import alexanders.mods.aoa.net.ItemUsePacket
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.item.ItemInstance
import de.ellpeck.rockbottom.api.world.IWorld
import de.ellpeck.rockbottom.api.world.layer.TileLayer
import org.lwjgl.opengl.Display


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
                net.sendToServer(ItemUsePacket(angle(x, y), player.uniqueId, Keys.KEY_SET_WAYPOINT.isDown))
            } else {
                ItemUsePacket(angle(x, y), player.uniqueId, Keys.KEY_SET_WAYPOINT.isDown).handle(game, null)
            }
        }
        return false
    }

    private fun angle(mouseX: Int, mouseY: Int): FloatArray {
        val w = Display.getWidth()
        val h = Display.getHeight()
        val radians = Math.atan2(mouseY - (h / 2.0), mouseX - (w / 2.0))
        return floatArrayOf(Math.cos(radians).toFloat(), Math.sin(radians).toFloat())
    }
}
