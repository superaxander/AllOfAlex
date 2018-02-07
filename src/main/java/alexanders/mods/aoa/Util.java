package alexanders.mods.aoa;

public class Util {
    public static <E extends Enum, T extends Enum & Cycleable<E>> E cycleEnum(T e) {
        return e.vals()[(e.ordinal() + 1) % e.vals().length];
    }
}
