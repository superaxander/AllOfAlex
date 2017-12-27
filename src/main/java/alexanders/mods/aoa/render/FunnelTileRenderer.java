package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.FunnelTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class FunnelTileRenderer extends DefaultTileRenderer<FunnelTile> {
    public FunnelTileRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, FunnelTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        switch (state.get(FunnelTile.direction)) {
            case 0: // Down
                manager.getTexture(texture.addSuffix(".down")).draw(renderX, renderY, scale, scale, light);
                break;
            case 1: // Right
                manager.getTexture(texture.addSuffix(".right")).draw(renderX, renderY, scale, scale, light);
                break;
            case 2: // Left
                ITexture t = manager.getTexture(texture.addSuffix(".right"));
                t.draw(renderX + scale, renderY, renderX, renderY + scale, 0, 0, t.getWidth(), t.getHeight(), light); // Draw flipped
                break;
            default:
                throw new IllegalStateException("Direction not supported");
        }
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, FunnelTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        manager.getTexture(texture.addSuffix(".down")).draw(x, y, scale, scale, filter);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IGraphics g, FunnelTile tile, TileState state) {
        return manager.getTexture(texture.addSuffix(".down"));
    }
}
