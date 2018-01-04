package alexanders.mods.aoa.render;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.tile.ColourableTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.tile.ColourableTile.COLOUR;

public class ColourableTileRenderer<T extends ColourableTile> extends DefaultTileRenderer<T> {
    public ColourableTileRenderer(IResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        manager.getTexture(this.texture).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light, state.get(COLOUR).colour);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, T tile, ItemInstance instance, float x, float y, float scale, int filter) {
        super.renderItem(game, manager, g, tile, instance, x, y, scale, Colors.multiply(Colours.get(instance.getMeta()).colour, filter));
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IGraphics g, T tile, TileState state) {
        return manager.getTexture(state.get(COLOUR).resourceName);
    }
}
