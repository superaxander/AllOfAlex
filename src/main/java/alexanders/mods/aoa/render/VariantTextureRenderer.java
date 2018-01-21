package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.VariantTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class VariantTextureRenderer<T extends VariantTile> implements ITileRenderer<T> {
    protected ITexture[] textures;

    public VariantTextureRenderer(ITexture... textures) {
        this.textures = textures;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, VariantTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        int variant = tile.getVariant(world, x, y);

        if (variant >= tile.variant.getVariants() / 2) {
            ITexture texture = textures[variant - tile.variant.getVariants() / 2];
            texture.draw(renderX + scale, renderY, renderX, renderY + scale, 0, 0, texture.getRenderWidth(), texture.getRenderHeight(), light); 
        } else
            textures[variant].draw(renderX, renderY, scale, scale, light);

    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, VariantTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        textures[0].draw(x, y, scale, scale, filter);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, VariantTile tile, TileState state) {
        return textures[0];
    }
}
