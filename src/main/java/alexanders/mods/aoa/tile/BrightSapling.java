package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.gen.BrightTreeGen;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.resourceBrightSapling;

public class BrightSapling extends TileBasic {
    private static final BrightTreeGen treeGenerator = new BrightTreeGen();

    public BrightSapling() {
        super(resourceBrightSapling);
        addProps(StaticTileProps.SAPLING_GROWTH);
    }

    @Override
    public boolean isFullTile() {
        return false;
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer) {
        return null;
    }

    @Override
    public void updateRandomly(IWorld world, int x, int y, TileLayer layer) {
        if (Util.RANDOM.nextInt(4) > 2) { // 3/4 chance
            TileState state = world.getState(layer, x, y);
            if (state.get(StaticTileProps.SAPLING_GROWTH) >= 4) {
                IChunk chunk = world.getChunk(x, y);
                treeGenerator.trySpawnTree(world, chunk, x - chunk.getX(), y - chunk.getY());
            } else {
                world.setState(layer, x, y, state.cycleProp(StaticTileProps.SAPLING_GROWTH));
            }
        }
    }
}
