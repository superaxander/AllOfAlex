package alexanders.mods.aoa.net;

import alexanders.mods.aoa.tile.entity.Note;
import alexanders.mods.aoa.tile.entity.SoundType;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.ISound;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class NotePlayPacket implements IPacket {
    private SoundType type;
    private Note note;
    private int x;
    private int y;
    private int z;

    public NotePlayPacket() {

    }

    public NotePlayPacket(SoundType type, Note note, int x, int y, int z) {
        this.type = type;
        this.note = note;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException {
        buf.writeInt(type.ordinal());
        buf.writeInt(note.ordinal());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException {
        type = SoundType.values()[buf.readInt()];
        note = Note.values()[buf.readInt()];
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        ISound sound = RockBottomAPI.getGame().getAssetManager().getSound(type.name.addSuffix(note.name));
        sound.playAt(x + .5, y + .5, z);
    }
}
