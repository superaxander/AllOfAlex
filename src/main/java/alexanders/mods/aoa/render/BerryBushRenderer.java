package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.BerryBushTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BerryBushRenderer<T extends BerryBushTile> extends VariantTextureRenderer<T> {
    private ITexture[] emptyTextures;

    public BerryBushRenderer(ITexture[] textures, ITexture... emptyTextures) {
        super(textures);
        this.emptyTextures = emptyTextures;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, BerryBushTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        if (state.get(BerryBushTile.empty)) {
            int variant = tile.getVariant(world, x, y);

            if (variant >= tile.variant.getVariants() / 2) {
                ITexture texture = emptyTextures[variant - tile.variant.getVariants() / 2];
                texture.draw(renderX + scale, renderY, renderX, renderY + scale, 0, 0, texture.getTextureWidth(), texture.getTextureHeight(), light);
            } else
                emptyTextures[variant].draw(renderX, renderY, scale, scale, light);
        } else
            super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, BerryBushTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        emptyTextures[0].draw(x, y, scale, scale, filter);
    }
}
