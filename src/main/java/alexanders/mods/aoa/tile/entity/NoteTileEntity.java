package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class NoteTileEntity extends TileEntity {
    public SoundType type = SoundType.PIANO;
    public Note note = Note.C;
    public long lastUse = 0;

    public NoteTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        set.addInt("type", type.ordinal());
        set.addInt("note", note.ordinal());
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        type = SoundType.values()[set.getInt("type")];
        note = Note.values()[set.getInt("note")];
    }

    @Override
    public boolean doesTick() {
        return false;
    }
}
