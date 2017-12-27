package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.ItemCannonTile;
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

public class ItemCannonRenderer extends DefaultTileRenderer<ItemCannonTile> {
    public ItemCannonRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, ItemCannonTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        ITexture t = manager.getTexture(texture.addSuffix(".head"));
        t.setRotationCenter(scale * 0.5f, scale / 2);
        t.setRotation(360 - state.get(ItemCannonTile.rotation) * 5);
        t.draw(renderX, renderY - scale / 4, scale * 2, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, ItemCannonTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        manager.getTexture(texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
    }
}
