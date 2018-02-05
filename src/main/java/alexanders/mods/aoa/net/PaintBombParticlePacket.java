package alexanders.mods.aoa.net;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.render.PaintBombParticle;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PaintBombParticlePacket implements IPacket {
    private int x;
    private int y;
    private Colours colour;

    public PaintBombParticlePacket() {
    }

    public PaintBombParticlePacket(int x, int y, Colours colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(colour.ordinal());
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        x = buf.readInt();
        y = buf.readInt();
        colour = Colours.get(buf.readInt());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        game.getParticleManager().addParticle(new PaintBombParticle(game.getWorld(), x, game.getWorld().getLowestAirUpwards(TileLayer.MAIN, x, y, true) + .5, Util.RANDOM.nextDouble() - .5, Util.RANDOM.nextDouble(), 100, colour));
    }
}
