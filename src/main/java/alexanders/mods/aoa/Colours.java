package alexanders.mods.aoa;

import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public enum Colours {
    WHITE(Colors.WHITE), RED(Colors.RED), BLUE(0xFF0000FF), GREEN(Colors.GREEN), ORANGE(Colors.ORANGE), BROWN(0xFF964B00), YELLOW(Colors.YELLOW), PINK(Colors.PINK),
    MAGENTA(Colors.MAGENTA);

    private static Colours[] values = values();
    public int colour;
    public IResourceName resourceName;

    Colours(int colour) {
        this.colour = colour;
        this.resourceName = createRes("particles." + this);
    }

    public static Colours get(int index) {
        return values[index];
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
