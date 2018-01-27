package alexanders.mods.aoa.init;

import alexanders.mods.aoa.Colours;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.assets.texture.ImageBuffer;

import java.util.HashMap;

public class Assets {
    public static HashMap<Colours, ITexture> colours = new HashMap<>();

    public static void init(IAssetManager assetManager) {
        for (Colours colour : Colours.values()) {
            ImageBuffer img = new ImageBuffer(1, 1);
            img.setRGBA(0, 0, colour.r, colour.g, colour.b, colour.a);
            assetManager.getTextureStitcher().loadTexture(colour.toString(), img, (x, y, texture) -> colours.put(colour, texture));
        }
    }
}
