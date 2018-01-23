package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.tile.state.TileState;

public class LoadedMenuTheme implements IMainMenuTheme {
    private final int color;
    private final TileState[][] map;
    
    public LoadedMenuTheme(int color, TileState[][] map) {
        this.color = color;
        this.map = map;
    }

    @Override
    public TileState getState(int x, int y, TileState[][] grid) {
        return map[x][y];
    }

    @Override
    public int getBackgroundColor() {
        return color;
    }
}
