package alexanders.mods.aoa;

import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public enum Colours {
    WHITE(Colors.WHITE), RED(Colors.RED), BLUE(0xFF0000FF), GREEN(Colors.GREEN), ORANGE(Colors.ORANGE), BROWN(0xFF964B00), YELLOW(Colors.YELLOW), PINK(Colors.PINK),
    MAGENTA(Colors.MAGENTA);

    private static Colours[] values = values();
    public int colour;
    public int r = Colors.getRInt(colour);
    public int g = Colors.getGInt(colour);
    public int b = Colors.getBInt(colour);
    public int a = Colors.getAInt(colour);
    public ResourceName resourceName;

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
