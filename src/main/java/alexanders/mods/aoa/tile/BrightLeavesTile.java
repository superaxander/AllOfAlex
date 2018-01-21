package alexanders.mods.aoa.tile;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

import static alexanders.mods.aoa.init.Resources.resourceBrightLeaves;
import static alexanders.mods.aoa.init.Tiles.brightSapling;
import static de.ellpeck.rockbottom.api.StaticTileProps.NATURAL;

public class BrightLeavesTile extends TileBasic {
    public BrightLeavesTile() {
        super(resourceBrightLeaves);
        addProps(NATURAL);
    }

    private boolean isNatural(IWorld world, int x, int y, TileLayer layer) {
        return world.getState(layer, x, y).get(NATURAL);
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer) {
        if (isNatural(world, x, y, layer))
            return null;
        return super.getBoundBox(world, x, y, layer);
    }

    @Override
    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        return super.getPlacementState(world, x, y, layer, instance, placer).prop(NATURAL, false);
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        return Util.RANDOM.nextInt(4) > 2 ? Collections.singletonList(new ItemInstance(brightSapling)) : Collections.emptyList();
    }

    @Override
    public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        if (!world.isClient() && isNatural(world, x, y, layer)) {
            for (int i = -2; i < 2; i++) {
                for (int j = -4; j < 4; j++) {
                    if (world.getState(layer, x + i, y + j).getTile().doesSustainLeaves(world, x + i, y + j, layer))
                        return;
                }
            }
            world.scheduleUpdate(x, y, layer, Util.RANDOM.nextInt(25) + 5);
        }
    }

    @Override
    public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer, int scheduledMeta) {
        if (!world.isClient() && isNatural(world, x, y, layer))
            world.destroyTile(x, y, layer, null, true);
    }
}
