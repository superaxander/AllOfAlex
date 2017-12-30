package alexanders.mods.aoa.render;

import alexanders.mods.aoa.item.IDamageable;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class DamageableRenderer<T extends Item & IDamageable> extends DefaultItemRenderer<T> {

    public DamageableRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, float x, float y, float scale, int filter) {
        super.render(game, manager, g, item, instance, x, y, scale, filter);
        int meta = instance.getMeta();
        if (meta != 0) {
            float x0 = x + scale * (1 / 12f);
            float y0 = y + scale * (10 / 12f);
            float width = scale * (10 / 12f);
            g.fillRect(x0, y0, width, scale / 12, Colors.RED);
            g.fillRect(x0, y0, width * (128 - instance.getMeta()) / item.getHighestPossibleMeta(), scale / 12, Colors.GREEN);
        }
    }
}
