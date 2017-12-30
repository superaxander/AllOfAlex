package alexanders.mods.aoa.net

import alexanders.mods.aoa.item.Useable
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.net.packet.IPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import java.nio.charset.StandardCharsets
import java.util.*


class ItemUsePacket(private var mouseDirection: FloatArray?, private var uuid: UUID?, private var shiftPressed: Boolean) : IPacket {
    constructor() : this(null, null, false)

    override fun toBuffer(buffer: ByteBuf) {
        buffer.writeFloat(if (mouseDirection == null) 0f else (mouseDirection as FloatArray)[0])
        buffer.writeFloat(if (mouseDirection == null) 0f else (mouseDirection as FloatArray)[1])
        buffer.writeBytes(uuid.toString().toByteArray(StandardCharsets.UTF_8))
        buffer.writeBoolean(shiftPressed)
    }

    override fun handle(game: IGameInstance, channelHandlerContext: ChannelHandlerContext?) {
        // Always ran on server:
        //println(uuid.toString())
        game.enqueueAction({ gameInstance, _ ->
            val p = gameInstance.world.getEntity(uuid) as AbstractEntityPlayer
            val itemInstance = p.inv[p.selectedSlot]
            if (itemInstance != null) {
                val itemType = itemInstance.item
                if (itemType is Useable && mouseDirection != null) {
                    itemType.use(itemInstance, mouseDirection as FloatArray, p, shiftPressed)
                }
            }
        }, null)
    }

    override fun fromBuffer(buffer: ByteBuf) {
        mouseDirection = floatArrayOf(buffer.readFloat(), buffer.readFloat())
        uuid = UUID.fromString(buffer.readBytes(36).toString(StandardCharsets.UTF_8))
        shiftPressed = buffer.readBoolean()
    }
}
