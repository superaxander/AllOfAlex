package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.data.settings.Keybind;
import org.lwjgl.input.Keyboard;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class Keys {
    public static Keybind KEY_ROTATE;
    public static Keybind KEY_SET_WAYPOINT;

    public static void init() {
        KEY_ROTATE = new Keybind(createRes("rotate"), Keyboard.KEY_LCONTROL, false);
        KEY_SET_WAYPOINT = new Keybind(createRes("set_waypoint"), Keyboard.KEY_LSHIFT, false);
        KEY_ROTATE.register();
        KEY_SET_WAYPOINT.register();
    }
}
