package alexanders.mods.aoa.net;

import alexanders.mods.aoa.JukeboxRunner;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.URL;

public class JukeboxStopPacket implements IPacket{
    private String url;
    
    public JukeboxStopPacket() {}
    public JukeboxStopPacket(String url) {
        this.url = url;
    }
    
    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        NetUtil.writeStringToBuffer(url, buf);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        url = NetUtil.readStringFromBuffer(buf);
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        JukeboxRunner.stop(url);
    }
}
