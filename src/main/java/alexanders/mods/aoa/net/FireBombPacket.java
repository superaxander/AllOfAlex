package alexanders.mods.aoa.net;

import alexanders.mods.aoa.entity.BombEntity;
import alexanders.mods.aoa.entity.MiningBombEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class FireBombPacket implements IPacket {
    private int x;
    private int y;
    private float[] angle;
    private boolean mining;

    public FireBombPacket() {

    }

    public FireBombPacket(int x, int y, float[] angle, boolean mining) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.mining = mining;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeFloat(angle[0]);
        buf.writeFloat(angle[1]);
        buf.writeBoolean(mining);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        x = buf.readInt();
        y = buf.readInt();
        angle[0] = buf.readFloat();
        angle[1] = buf.readFloat();
        mining = buf.readBoolean();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        BombEntity entity;
        if (mining)
            entity = new MiningBombEntity(world);
        else
            entity = new BombEntity(world);
        entity.x = x;
        entity.y = y + 1.5;
        entity.motionX = .5 * angle[0];
        entity.motionY = -0.5 * angle[1];
        world.addEntity(entity);
    }
}
