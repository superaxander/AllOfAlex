package alexanders.mods.aoa.gen;

import alexanders.mods.aoa.init.Tiles;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Random;

import static alexanders.mods.aoa.init.Tiles.brightSapling;

public class BrightTreeGen implements IWorldGenerator {
    private static final int SPAWN_ATTEMPTS = 12;
    private static final int LEAF_HEIGHT = 3;
    private static final TileState logBottom = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.TRUNK_BOTTOM);
    private static final TileState logMiddle = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.TRUNK_MIDDLE);
    private static final TileState logTop = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.TRUNK_TOP);
    private static final TileState rootLeft = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.ROOT_LEFT);
    private static final TileState rootRight = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.ROOT_RIGHT);
    private static final TileState branchLeft = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.BRANCH_LEFT);
    private static final TileState branchRight = Tiles.brightLog.getDefState().prop(StaticTileProps.LOG_VARIANT, StaticTileProps.LogType.BRANCH_RIGHT);
    private static final TileState leaves = Tiles.brightLeaves.getDefState().prop(StaticTileProps.NATURAL, true);

    private final Random chunkRandom = new Random();
    private final Random posRandom = new Random();

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return true;
    }

    @Override
    public void generate(IWorld world, IChunk chunk) {
        chunkRandom.setSeed(Util.scrambleSeed(chunk.getGridX(), chunk.getGridY(), world.getSeed()));
        int treeAmount = Util.ceil(chunkRandom.nextInt(4) + 1);
        for (int i = 0; i < treeAmount; i++) {
            // Generate tree
            for (int attempt = 0; attempt < SPAWN_ATTEMPTS; attempt++) {
                int x = chunkRandom.nextInt(22) + 5;
                int y = chunk.getLowestAirUpwardsInner(TileLayer.MAIN, x, 1, true);
                if (y < 0)
                    continue;
                if (chunk.getBiomeInner(x, y).canTreeGrow(world, chunk, x, y))
                    if (trySpawnTree(world, chunk, x, y))
                        break;
            }
        }
    }

    public boolean trySpawnTree(IWorld world, IChunk chunk, int x, int y) {
        posRandom.setSeed(Util.scrambleSeed(x, y, world.getSeed()));
        int height = posRandom.nextInt(5) + 3;
        int rootDirection = posRandom.nextInt(4); // 0: no roots, 1: left, 2: right, 3: L+R
        boolean branches = posRandom.nextBoolean();
        for (int i = 0; i < height; i++)
            if (checkTile(world, chunk, x, y + i))
                return false;

        if (checkTile(world, chunk, x - 1, y) || checkTile(world, chunk, x + 1, y))
            return false;
        if (checkTile(world, chunk, x - 1, y + height - 1) || checkTile(world, chunk, x - 1, y + height - 1))
            return false;

        // All log tiles clear place tree
        place(world, chunk, x, y, logBottom);
        for (int i = 1; i < height - 1; i++)
            place(world, chunk, x, y + i, logMiddle);
        place(world, chunk, x, y + height - 1, logTop);
        switch (rootDirection) {
            case 1:
                if (chunk.getStateInner(TileLayer.MAIN, x - 1, y - 1).getTile().isFullTile())
                    place(world, chunk, x - 1, y, rootLeft);
                break;
            case 2:
                if (chunk.getStateInner(TileLayer.MAIN, x + 1, y - 1).getTile().isFullTile())
                    place(world, chunk, x + 1, y, rootRight);
                break;
            case 3:
                if (chunk.getStateInner(TileLayer.MAIN, x - 1, y - 1).getTile().isFullTile())
                    place(world, chunk, x - 1, y, rootLeft);
                if (chunk.getStateInner(TileLayer.MAIN, x + 1, y - 1).getTile().isFullTile())
                    place(world, chunk, x + 1, y, rootRight);
                break;
        }
        if (branches) {
            place(world, chunk, x - 1, y + height - 1, branchLeft);
            place(world, chunk, x + 1, y + height - 1, branchRight);
        }
        int radius = posRandom.nextInt(2 + (branches ? 1 : 0)) + 2;
        for (int j = 0; j < LEAF_HEIGHT; j++) {
            for (int i = 0; i < (j == LEAF_HEIGHT - 1 ? radius - 1 : radius); i++) {
                checkAndPlace(world, chunk, x + i, y + height + j, leaves);
                checkAndPlace(world, chunk, x - i, y + height + j, leaves);
            }
        }
        return true;
    }

    private void checkAndPlace(IWorld world, IChunk chunk, int x, int y, TileState state) {
        if (!checkTile(world, chunk, x, y))
            place(world, chunk, x, y, state);
    }

    private void place(IWorld world, IChunk chunk, int x, int y, TileState state) {
        chunk.setStateInner(TileLayer.MAIN, x, y, state);
    }

    private boolean checkTile(IWorld world, IChunk chunk, int x, int y) {
        Tile tile = chunk.getStateInner(TileLayer.MAIN, x, y).getTile();
        return !world.isPosLoaded(x + chunk.getX(), y + chunk.getY()) || tile != brightSapling && !tile.canReplace(world, x, y, TileLayer.MAIN);
    }

    @Override
    public int getPriority() {
        return -90;
    }
}
