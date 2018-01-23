package alexanders.mods.aoa.net;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ExportMenuPacket implements IPacket {
    private String saveLocation;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public ExportMenuPacket() {
    }

    public ExportMenuPacket(String saveLocation, int x1, int y1, int x2, int y2) {
        this.saveLocation = saveLocation;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        char[] locBuf = new char[8];
        saveLocation.getChars(0, 8, locBuf, 0);
        buf.writeCharSequence(new String(locBuf), StandardCharsets.UTF_8);
        buf.writeInt(x1);
        buf.writeInt(y1);
        buf.writeInt(x2);
        buf.writeInt(y2);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        saveLocation = buf.readCharSequence(8, StandardCharsets.UTF_8).toString().replace("\0", ""); // Remove any trimmings
        for (char c : saveLocation.toCharArray())
            if (!Character.isLetterOrDigit(c)) // Protection from malicious servers
                throw new RuntimeException("Path contains invalid character");
        x1 = buf.readInt();
        y1 = buf.readInt();
        x2 = buf.readInt();
        y2 = buf.readInt();
    }

    //private static final char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()+_=,./:;'\"{}[]|\\§ÞÇ¹òÈ╚Ä╔╩╦├┼ú".toCharArray();

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        if (saveLocation != null) {
            int loc = 0;
            char next;
            IWorld world = game.getWorld();
            HashMap<TileState, Character> t_s = new HashMap<>();
            int startX = Math.min(x1, x2);
            int endX = Math.max(x1, x2);
            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            if (endY - startY != 16 || endX - startX != 16)
                throw new RuntimeException("Invalid menu size");
            try {
                Path dir = Paths.get("rockbottom", "themes");
                if (!Files.isDirectory(dir))
                    Files.createDirectory(dir);
                int i = 0;
                DataOutputStream stream = new DataOutputStream(new FileOutputStream(dir.resolve(saveLocation).toFile()));
                for (int y = startY; y < endY; y++) {
                    for (int x = startX; x < endX; x++) {
                        TileState state = world.getState(TileLayer.MAIN, x, y);
                        if (state == null)
                            stream.writeChar('-');
                        else {
                            if (!t_s.containsKey(state))
                                t_s.put(state, (next = (char) loc++) == '\n' || next == '=' || next == '-' ? (char) loc++ : next);
                            stream.writeChar(t_s.get(state));
                        }
                        i++;
                    }
                }
                System.out.println(i);
                stream.writeChar('\n');
                t_s.forEach((state, c) -> {
                    try {
                        stream.writeChar(c);
                        stream.writeChar('=');
                        stream.writeChars(state.getName().toString());
                        stream.writeChar('\n');
                    } catch (IOException e) {
                        throw new UncheckedIOException("Something very weird is going on!", e);
                    }
                });
                stream.flush();
                stream.close();
                game.getChatLog().displayMessage(new ChatComponentText("Wrote menu to: rockbottom/themes/" + saveLocation));
            } catch (IOException e) {
                e.printStackTrace();
                game.getChatLog().displayMessage(new ChatComponentText(FormattingCode.RED + "Unable to write file!"));
            }
        }
    }
}
