package alexanders.mods.aoa.net;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.entity.BombEntity;
import alexanders.mods.aoa.entity.MiningBombEntity;
import alexanders.mods.aoa.entity.PaintBombEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class FireBombPacket implements IPacket {
    private double x;
    private double y;
    private float[] angle;
    private int type;
    private Colours colour = Colours.WHITE;

    public FireBombPacket() {

    }

    public FireBombPacket(double x, double y, float[] angle, int type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.type = type;
    }

    public FireBombPacket(double x, double y, float[] angle, int type, Colours colour) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.type = type;
        this.colour = colour;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeFloat(angle[0]);
        buf.writeFloat(angle[1]);
        buf.writeInt(type);
        buf.writeInt(colour.ordinal());
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        angle[0] = buf.readFloat();
        angle[1] = buf.readFloat();
        type = buf.readInt();
        colour = Colours.get(buf.readInt());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        BombEntity entity;
        switch (type) {
            case 0:
                entity = new MiningBombEntity(world);
                break;
            case 1:
                entity = new BombEntity(world);
                break;
            case 2:
                entity = new PaintBombEntity(world, colour);
                break;
            default:
                throw new IllegalStateException("Invalid FireBombPacket");
        }
        entity.x = x;
        entity.y = y + 1.5;
        entity.motionX = .5 * angle[0];
        entity.motionY = -0.5 * angle[1];
        world.addEntity(entity);
    }
}
