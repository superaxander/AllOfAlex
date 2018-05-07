package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.render.LogRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.resourceBrightLog;
import static de.ellpeck.rockbottom.api.StaticTileProps.LOG_VARIANT;

public class BrightLogTile extends TileBasic {
    public BrightLogTile() {
        super(resourceBrightLog);
        addProps(LOG_VARIANT);
        this.setForceDrop();
        addEffectiveTool(ToolType.AXE, 1);
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new LogRenderer(name);
    }

    private StaticTileProps.LogType getType(IWorld world, int x, int y, TileLayer layer) {
        return world.getState(layer, x, y).get(LOG_VARIANT);
    }

    private StaticTileProps.LogType getType(IWorld world, int x, int y) {
        return world.getState(x, y).get(LOG_VARIANT);
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer) {
        if (getType(world, x, y, layer).isNatural()) return null;
        return super.getBoundBox(world, x, y, layer);
    }

    @Override
    public float getHardness(IWorld world, int x, int y, TileLayer layer) {
        if (getType(world, x, y, layer).isNatural()) return 9.0f;
        else return 3.0f;
    }

    @Override
    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        super.onDestroyed(world, x, y, destroyer, layer, shouldDrop);
        if (destroyer != null && !world.isClient() && getType(world, x, y, layer).isNatural()) { // Destroyed by player, not on client and natural
            boolean working = true;
            for (int i = 0; working; i++) {
                working = false;
                for (int dir = -1; dir < 2; dir++) {
                    TileState state = world.getState(layer, x + dir, y + i);
                    if (state.getTile() == this && state.get(LOG_VARIANT).isNatural()) { // Is of same type and natural
                        world.scheduleUpdate(x + dir, y + i, layer, i * 3);
                        working = true;
                    }
                }
            }
        }
    }

    @Override
    public boolean doesSustainLeaves(IWorld world, int x, int y, TileLayer layer) {
        return getType(world, x, y, layer).isNatural();
    }

    @Override
    public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer, int scheduledMeta) {
        if (!world.isClient() && getType(world, x, y, layer).isNatural()) world.destroyTile(x, y, layer, null, true);
    }

    public final boolean hasSolidSurface(IWorld world, int x, int y, TileLayer layer) {
        return !getType(world, x, y, layer).isNatural();
    }

    public final boolean canLiquidSpreadInto(IWorld world, int x, int y, TileLiquid liquid) {
        StaticTileProps.LogType type;
        return (type = getType(world, x, y)) != StaticTileProps.LogType.TRUNK_BOTTOM && type != StaticTileProps.LogType.TRUNK_MIDDLE;
    }

    public final boolean canBreak(IWorld world, int x, int y, TileLayer layer) {
        StaticTileProps.LogType type;
        return !(type = getType(world, x, y, layer)).isNatural() || type == StaticTileProps.LogType.TRUNK_BOTTOM || type == StaticTileProps.LogType.TRUNK_MIDDLE;
    }

    public final boolean obscuresBackground(IWorld world, int x, int y, TileLayer layer) {
        StaticTileProps.LogType type;
        return !(type = getType(world, x, y, layer)).isNatural() || type == StaticTileProps.LogType.TRUNK_BOTTOM || type == StaticTileProps.LogType.TRUNK_MIDDLE;
    }
}
