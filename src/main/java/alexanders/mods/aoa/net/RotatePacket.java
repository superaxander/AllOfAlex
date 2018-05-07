package alexanders.mods.aoa.net;

import alexanders.mods.aoa.tile.FunnelTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class RotatePacket implements IPacket {
    private int x;
    private int y;
    private TileLayer layer;
    private int dir;

    public RotatePacket() {

    }

    public RotatePacket(int x, int y, TileLayer layer, int dir) {
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.dir = dir;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        if (layer == null) throw new IllegalStateException("Layer is null!");
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(layer.index());
        buf.writeInt(dir);
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        layer = TileLayer.getAllLayers().get(buf.readInt());
        dir = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.getWorld().setState(layer, x, y, game.getWorld().getState(layer, x, y).prop(FunnelTile.direction, dir));
    }
}
