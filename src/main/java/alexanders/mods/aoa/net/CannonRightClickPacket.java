package alexanders.mods.aoa.net;

import alexanders.mods.aoa.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.UUID;

public class CannonRightClickPacket implements IPacket {
    private UUID player;
    private boolean doRotate;
    private int x;
    private int y;
    private TileLayer layer;

    public CannonRightClickPacket() {

    }

    public CannonRightClickPacket(UUID player, boolean doRotate, int x, int y, TileLayer layer) {
        this.player = player;
        this.doRotate = doRotate;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        if (player == null || layer == null)
            throw new IllegalStateException("Player or layer not defined");
        buf.writeLong(player.getMostSignificantBits());
        buf.writeLong(player.getLeastSignificantBits());
        buf.writeBoolean(doRotate);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(layer.index());
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        player = new UUID(buf.readLong(), buf.readLong());
        doRotate = buf.readBoolean();
        x = buf.readInt();
        y = buf.readInt();
        layer = TileLayer.getAllLayers().get(buf.readInt());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        ItemCannonTile.onRightClick(world, x, y, layer, doRotate, world.getPlayer(player));
    }
}
