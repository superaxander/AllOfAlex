package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.tile.state.TileState;

import java.util.Random;

import static alexanders.mods.aoa.init.Tiles.*;

public class RandomMenuTheme implements IMainMenuTheme {
    private Random random;

    public RandomMenuTheme() {
        random = new Random();
    }

    @Override
    public TileState getState(int x, int y, TileState[][] grid) {
        switch (random.nextInt(36)) {
            case 0:
                return bricksBlue.getDefState();
            case 1:
                return bricksGreen.getDefState();
            case 2:
                return bricksGrey.getDefState();
            case 3:
                return bricksOrangeBrown.getDefState();
            case 4:
                return bricksPurple.getDefState();
            case 5:
                return bricksRed.getDefState();
            case 6:
                return bricksYellow.getDefState();
            case 7:
                return bricksYellowBrown.getDefState();
            case 8:
                return colourisedWoodAqua.getDefState();
            case 9:
                return colourisedWoodGoldBrown.getDefState();
            case 10:
                return colourisedWoodGreen.getDefState();
            case 11:
                return colourisedWoodGreenBrown.getDefState();
            case 12:
                return colourisedWoodPinkPurple.getDefState();
            case 13:
                return crystalBlue.getDefState();
            case 14:
                return crystalGreen.getDefState();
            case 15:
                return crystalPurple.getDefState();
            case 16:
                return crystalRed.getDefState();
            case 17:
                return crystalYellow.getDefState();
            case 18:
                return smoothStoneBlue.getDefState();
            case 19:
                return smoothStoneGreen.getDefState();
            case 20:
                return smoothStoneGrey.getDefState();
            case 21:
                return smoothStoneOrange.getDefState();
            case 22:
                return smoothStonePurple.getDefState();
            case 23:
                return smoothStoneYellow.getDefState();
            case 24:
                return pillarHorizontalBeige.getDefState();
            case 25:
                return pillarVerticalBeige.getDefState();
            case 26:
                return pillarHorizontalBlue.getDefState();
            case 27:
                return pillarVerticalBlue.getDefState();
            case 28:
                return pillarHorizontalGreen.getDefState();
            case 29:
                return pillarVerticalGreen.getDefState();
            case 30:
                return pillarHorizontalGrey.getDefState();
            case 31:
                return pillarVerticalGrey.getDefState();
            case 32:
                return pillarHorizontalPurple.getDefState();
            case 33:
                return pillarVerticalPurple.getDefState();
            case 34:
                return pillarHorizontalPurpleBlue.getDefState();
            case 35:
                return pillarVerticalPurpleBlue.getDefState();
            case 36:
                return slime.getDefState();
            case 37:
                return pearlOre.getDefState();
            case 38:
                return brightLog.getDefState();
            case 39:
                return brightLeaves.getDefState();
        }
        return null;
    }

    @Override
    public int getBackgroundColor() {
        return 0;
    }
}
