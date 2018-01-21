package alexanders.mods.aoa;

import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.tile.ItemConduitTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.resourceConduitLayer;

public class ConduitLayer extends TileLayer {
    public static ConduitLayer instance;

    public ConduitLayer() {
        super(resourceConduitLayer, 10);
        instance = this;
    }

    @Override
    public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player) {
        return Keys.KEY_CONDUIT_LAYER.isDown();
    }

    @Override
    public boolean canCollide(MovableWorldObject object) {
        return false;
    }

    @Override
    public boolean canHoldTileEntities() {
        return true;
    }

    @Override
    public boolean canTileBeInLayer(IWorld world, int x, int y, Tile tile) {
        return tile != null && (tile instanceof ItemConduitTile || tile.isAir());
    }

    @Override
    public boolean isVisible(IGameInstance game, AbstractEntityPlayer player, IChunk chunk, int x, int y, boolean isRenderingForeground) {
        return Keys.KEY_CONDUIT_LAYER.isDown();
    }
}
