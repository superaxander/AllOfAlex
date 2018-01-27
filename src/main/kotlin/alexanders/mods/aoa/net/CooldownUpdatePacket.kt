package alexanders.mods.aoa.net

import alexanders.mods.aoa.item.ICooldownable
import de.ellpeck.rockbottom.api.IGameInstance
import de.ellpeck.rockbottom.api.data.set.DataSet
import de.ellpeck.rockbottom.api.net.packet.IPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext


class CooldownUpdatePacket(var cooldown: Int, var slot: Int) : IPacket {
    constructor() : this(0, 0)

    override fun toBuffer(buffer: ByteBuf) {
        buffer.writeInt(cooldown)
        buffer.writeInt(slot)
    }

    override fun handle(game: IGameInstance, channelHandlerContext: ChannelHandlerContext) {
        game.enqueueAction({ gameInstance, _ ->
            val slot = gameInstance.player.inv[this.slot]
            //println("received cooldown update")
            if (slot != null && slot.additionalData == null && slot.item is ICooldownable) {
                slot.additionalData = DataSet()
                slot.additionalData.addInt("cooldown", cooldown)
            } else if (slot != null && slot.additionalData != null && slot.additionalData.hasKey("cooldown")) {
                slot.additionalData.addInt("cooldown", cooldown)
                if (cooldown == 60 && slot.removeAmount(1).amount <= 0)
                    gameInstance.player.inv[this.slot] = null
            }
        }, null)
    }

    override fun fromBuffer(buffer: ByteBuf) {
        cooldown = buffer.readInt()
        slot = buffer.readInt()
    }
}
