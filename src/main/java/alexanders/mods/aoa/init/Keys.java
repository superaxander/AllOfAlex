package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.data.settings.Keybind;

import static alexanders.mods.aoa.init.Resources.*;
import static org.lwjgl.glfw.GLFW.*;

public class Keys {
    public static Keybind KEY_ROTATE;
    public static Keybind KEY_SET_WAYPOINT;
    public static Keybind KEY_CONDUIT_LAYER;
    public static Keybind KEY_OPEN_BOMB_LAUNCHER_INVENTORY;

    public static void init() {
        KEY_ROTATE = new Keybind(resourceRotateKey, GLFW_KEY_LEFT_CONTROL, false);
        KEY_SET_WAYPOINT = new Keybind(resourceSetWaypointKey, GLFW_KEY_LEFT_SHIFT, false);
        KEY_CONDUIT_LAYER = new Keybind(resourceConduitLayerKey, GLFW_KEY_H, false);
        KEY_OPEN_BOMB_LAUNCHER_INVENTORY = new Keybind(resourceOpenBombLauncherInventoryKey, GLFW_KEY_LEFT_SHIFT, false);
        KEY_ROTATE.register();
        KEY_SET_WAYPOINT.register();
        KEY_CONDUIT_LAYER.register();
        KEY_OPEN_BOMB_LAUNCHER_INVENTORY.register();
    }
}
