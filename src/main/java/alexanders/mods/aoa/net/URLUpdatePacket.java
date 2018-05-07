package alexanders.mods.aoa.net;

import alexanders.mods.aoa.tile.entity.JukeboxTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class URLUpdatePacket implements IPacket {
    private TileLayer layer;
    private int x;
    private int y;
    private String url;

    public URLUpdatePacket() {
    }

    public URLUpdatePacket(TileLayer layer, int x, int y, String url) {
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.url = url;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeInt(layer.index());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeBoolean(url != null);
        if (url != null) NetUtil.writeStringToBuffer(url, buf);
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        layer = TileLayer.getAllLayers().get(buf.readInt());
        x = buf.readInt();
        y = buf.readInt();
        if (buf.readBoolean()) url = NetUtil.readStringFromBuffer(buf);
        else url = null;
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.getWorld().getTileEntity(layer, x, y, JukeboxTileEntity.class).url = url;
    }
}
