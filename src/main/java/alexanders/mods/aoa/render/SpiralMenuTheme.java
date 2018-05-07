package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.tile.state.TileState;

import static alexanders.mods.aoa.init.Tiles.*;

public class SpiralMenuTheme implements IMainMenuTheme {
    private TileState[] map = new TileState[]{pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarVerticalBeige.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(),
            pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(),
            pillarHorizontalGrey.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarVerticalBeige.getDefState(), pillarVerticalGrey.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(),
            bricksBlue.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(), pillarVerticalGrey.getDefState(), pillarVerticalBeige.getDefState(),
            pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(),
            pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalGrey.getDefState(), bricksBlue.getDefState(),
            bricksGreen.getDefState(), bricksGreen.getDefState(), bricksGreen.getDefState(), bricksGreen.getDefState(), bricksBlue.getDefState(), pillarVerticalGrey.getDefState(),
            pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalBlue.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalBeige.getDefState(),
            pillarVerticalGrey.getDefState(), bricksBlue.getDefState(), bricksGreen.getDefState(), crystalRed.getDefState(), crystalRed.getDefState(), bricksGreen.getDefState(),
            bricksBlue.getDefState(), pillarVerticalGrey.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarVerticalBeige.getDefState(), pillarVerticalGrey.getDefState(), bricksBlue.getDefState(), bricksGreen.getDefState(), crystalRed.getDefState(),
            crystalRed.getDefState(), bricksGreen.getDefState(), bricksBlue.getDefState(), pillarVerticalGrey.getDefState(), pillarVerticalBeige.getDefState(),
            pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(),
            pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalGrey.getDefState(), bricksBlue.getDefState(),
            bricksGreen.getDefState(), bricksGreen.getDefState(), bricksGreen.getDefState(), bricksGreen.getDefState(), bricksBlue.getDefState(), pillarVerticalGrey.getDefState(),
            pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalBlue.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalBeige.getDefState(),
            pillarVerticalGrey.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(), bricksBlue.getDefState(),
            bricksBlue.getDefState(), pillarVerticalGrey.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarVerticalBeige.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(),
            pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(), pillarHorizontalGrey.getDefState(),
            pillarHorizontalGrey.getDefState(), pillarVerticalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarVerticalPurple.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(),
            pillarHorizontalBeige.getDefState(), pillarHorizontalBeige.getDefState(), pillarVerticalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarVerticalGreen.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(),
            pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarHorizontalPurple.getDefState(), pillarVerticalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarVerticalBlue.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(), pillarHorizontalGreen.getDefState(),
            pillarVerticalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(), pillarHorizontalBlue.getDefState(),
            pillarHorizontalBlue.getDefState()};

    @Override
    public TileState getState(int x, int y, TileState[][] grid) {
        return map[y * 16 + x];
    }

    @Override
    public int getBackgroundColor() {
        return 0;
    }
}
