package alexanders.mods.aoa.init;

import alexanders.mods.aoa.render.BerryBushRenderer;
import alexanders.mods.aoa.render.VariantTextureRenderer;
import alexanders.mods.aoa.tile.VariantTile;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public enum FoliageAssets {
    grass(4, 2),
    long_grass(4, 4),
    blue_berry_bush(2, 16),
    red_berry_bush(2, 16),
    ugly_plant(1, 18),
    large_ugly_plant(1, 20),
    dry_farmland(2, 0);

    public final int amount;
    public final int chance; // 1 in chance
    public VariantTextureRenderer renderer;
    public VariantTile tile;

    FoliageAssets(int amount, int chance) {
        this.amount = amount;
        this.chance = chance;
    }

    public static void init(IAssetManager manager) {
        dry_farmland.renderer = new VariantTextureRenderer(getTextures(manager, Resources.resourceDryFarmland, 2));
        grass.renderer = new VariantTextureRenderer(getTextures(manager, Resources.resourceGrass, 4));
        long_grass.renderer = new VariantTextureRenderer(getTextures(manager, Resources.resourceLongGrass, 4));
        blue_berry_bush.renderer = new BerryBushRenderer(getTextures(manager, Resources.resourceBlueBerryBush, 2), getEmptyTextures(manager, Resources.resourceBlueBerryBush, 2));
        red_berry_bush.renderer = new BerryBushRenderer(getTextures(manager, Resources.resourceRedBerryBush, 2), getEmptyTextures(manager, Resources.resourceRedBerryBush, 2));
        ugly_plant.renderer = new VariantTextureRenderer(getTextures(manager, Resources.resourceUglyPlant, 1));
        large_ugly_plant.renderer = new VariantTextureRenderer(getTextures(manager, Resources.resourceLargeUglyPlant, 1));
    }

    private static ITexture[] getEmptyTextures(IAssetManager manager, IResourceName name, int amount) {
        name = name.addSuffix("_empty");
        ITexture[] textures = new ITexture[amount];
        for (int i = 1; i <= amount; i++) {
            textures[i - 1] = manager.getTexture(name.addSuffix("." + i));
        }
        return textures;
    }

    private static ITexture[] getTextures(IAssetManager manager, IResourceName name, int amount) {
        ITexture[] textures = new ITexture[amount];
        for (int i = 1; i <= amount; i++) {
            textures[i - 1] = manager.getTexture(name.addSuffix("." + i));
        }
        return textures;
    }
}
