package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.data.settings.Keybind;

import static alexanders.mods.aoa.init.Resources.*;
import static org.lwjgl.glfw.GLFW.*;

public class Keys {
    public static Keybind KEY_ROTATE;
    public static Keybind KEY_SET_WAYPOINT;
    public static Keybind KEY_CONDUIT_LAYER;
    public static Keybind KEY_OPEN_BOMB_LAUNCHER_INVENTORY;
    public static Keybind KEY_CHANGE_NOTE;
    public static Keybind KEY_CHANGE_INSTRUMENT;
    public static Keybind KEY_REMOVE_FILTER;
    public static Keybind KEY_CYCLE_JUKEBOX;

    public static void init() {
        KEY_ROTATE = new Keybind(resourceRotateKey, GLFW_KEY_LEFT_CONTROL);
        KEY_SET_WAYPOINT = new Keybind(resourceSetWaypointKey, GLFW_KEY_LEFT_SHIFT);
        KEY_CONDUIT_LAYER = new Keybind(resourceConduitLayerKey, GLFW_KEY_H);
        KEY_OPEN_BOMB_LAUNCHER_INVENTORY = new Keybind(resourceOpenBombLauncherInventoryKey, GLFW_KEY_LEFT_SHIFT);
        KEY_CHANGE_NOTE = new Keybind(resourceChangeNoteKey, GLFW_KEY_LEFT_CONTROL);
        KEY_CHANGE_INSTRUMENT = new Keybind(resourceChangeInstrumentKey, GLFW_KEY_LEFT_ALT);
        KEY_REMOVE_FILTER = new Keybind(resourceRemoveFilterKey, GLFW_KEY_LEFT_CONTROL);
        KEY_CYCLE_JUKEBOX = new Keybind(resourceCycleJukeboxKey, GLFW_KEY_J);
        KEY_ROTATE.register();
        KEY_SET_WAYPOINT.register();
        KEY_CONDUIT_LAYER.register();
        KEY_OPEN_BOMB_LAUNCHER_INVENTORY.register();
        KEY_REMOVE_FILTER.register();
        KEY_CYCLE_JUKEBOX.register();
    }
}
