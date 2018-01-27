package alexanders.mods.aoa.net;

import alexanders.mods.aoa.init.Tiles;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class CannonRightClickPacket implements IPacket {
    private UUID player;
    private boolean doRotate;
    private int x;
    private int y;
    private TileLayer layer;
    private boolean isBombVariant;

    public CannonRightClickPacket() {

    }

    public CannonRightClickPacket(UUID player, boolean doRotate, int x, int y, TileLayer layer, boolean isBombVariant) {
        this.player = player;
        this.doRotate = doRotate;
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.isBombVariant = isBombVariant;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        if (player == null || layer == null)
            throw new IllegalStateException("Player or layer not defined");
        buf.writeLong(player.getMostSignificantBits());
        buf.writeLong(player.getLeastSignificantBits());
        buf.writeBoolean(doRotate);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(layer.index());
        buf.writeBoolean(isBombVariant);
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        player = new UUID(buf.readLong(), buf.readLong());
        doRotate = buf.readBoolean();
        x = buf.readInt();
        y = buf.readInt();
        layer = TileLayer.getAllLayers().get(buf.readInt());
        isBombVariant = buf.readBoolean();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        if (isBombVariant)
            Tiles.bombCannon.onRightClick(world, x, y, layer, doRotate, world.getPlayer(player));
        else
            Tiles.itemCannon.onRightClick(world, x, y, layer, doRotate, world.getPlayer(player));
    }
}
