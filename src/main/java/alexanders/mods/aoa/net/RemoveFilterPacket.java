package alexanders.mods.aoa.net;

import alexanders.mods.aoa.ConduitLayer;
import alexanders.mods.aoa.tile.entity.ItemConduitTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class RemoveFilterPacket implements IPacket {
    private int x;
    private int y;

    public RemoveFilterPacket() {
    }

    public RemoveFilterPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeInt(x);
        buf.writeInt(y);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        x = buf.readInt();
        y = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.getWorld().getTileEntity(ConduitLayer.instance, x, y, ItemConduitTileEntity.class).filter = null;
    }
}
