package alexanders.mods.aoa.init;

import alexanders.mods.aoa.commands.*;

public class Commands {
    public static void init() {
        new SetTileCommand().register();
        new FillCommand().register();
        new SpawnCommand().register();
        new ExportMenuCommand().register();
        new ClearCommand().register();
    }
}
