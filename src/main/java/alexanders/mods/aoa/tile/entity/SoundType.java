package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.Cycleable;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public enum SoundType implements Cycleable<SoundType> {
    PERCUSSSION("percussion"), PIANO("piano");
    public final ResourceName name;

    SoundType(String name) {
        this.name = createRes(name);
    }

    public SoundType[] vals() {
        return values();
    }
}
