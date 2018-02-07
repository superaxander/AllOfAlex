package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.Cycleable;

public enum Note implements Cycleable<Note> {
    A(".A"), A_SHARP(".A#"), B(".B"), C(".C"), C_SHARP(".C#"), D(".D"), D_SHARP(".D#"), E(".E"), F(".F"), F_SHARP(".F#"), G(".G");
    public final String name;

    Note(String name) {
        this.name = name;
    }

    @Override
    public Note[] vals() {
        return values();
    }
}
