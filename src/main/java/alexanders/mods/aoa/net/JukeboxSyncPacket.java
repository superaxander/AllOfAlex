package alexanders.mods.aoa.net;

import alexanders.mods.aoa.JukeboxRunner;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class JukeboxSyncPacket implements IPacket {
    private String url;
    private long playTime;
    
    public JukeboxSyncPacket(){}
    public JukeboxSyncPacket(String url, long playTime) {
        this.url = url;
        this.playTime = playTime;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        NetUtil.writeStringToBuffer(url, buf);
        buf.writeLong(playTime);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        url = NetUtil.readStringFromBuffer(buf);
        playTime = buf.readLong();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        JukeboxRunner.sync(url, playTime);
    }
}
