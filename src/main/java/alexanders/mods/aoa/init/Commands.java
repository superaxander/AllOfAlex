package alexanders.mods.aoa.init;

import alexanders.mods.aoa.commands.FillCommand;
import alexanders.mods.aoa.commands.SetTileCommand;
import alexanders.mods.aoa.commands.SpawnCommand;

public class Commands {
    public static void init() {
        new SetTileCommand().register();
        new FillCommand().register();
        new SpawnCommand().register();
    }
}
