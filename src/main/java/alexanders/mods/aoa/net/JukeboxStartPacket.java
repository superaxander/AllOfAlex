package alexanders.mods.aoa.net;

import alexanders.mods.aoa.JukeboxRunner;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class JukeboxStartPacket implements IPacket {
    private String url;

    public JukeboxStartPacket() {
    }

    public JukeboxStartPacket(String url) {
        this.url = url;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        if (url == null) buf.writeBoolean(false);
        else {
            buf.writeBoolean(true);
            NetUtil.writeStringToBuffer(url, buf);
        }
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        if (buf.readBoolean()) url = NetUtil.readStringFromBuffer(buf);
        else url = null;
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        JukeboxRunner.start(url);
    }
}
