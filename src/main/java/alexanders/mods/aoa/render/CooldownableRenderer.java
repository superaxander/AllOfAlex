package alexanders.mods.aoa.render;

import alexanders.mods.aoa.item.ICooldownable;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.cooldownResource;

public class CooldownableRenderer<T extends Item & ICooldownable> extends DefaultItemRenderer<T> {
    public CooldownableRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, T item, ItemInstance instance, float x, float y, float scale, int filter) {
        super.render(game, manager, g, item, instance, x, y, scale, filter);
        if (instance.getAmount() == -1)
            return;

        DataSet addData = instance.getAdditionalData();
        if (addData != null && addData.getInt("cooldown") > 0) {
            ITexture image = manager.getTexture(cooldownResource);
            image.draw(x, y, scale, (scale / item.getMaxCooldown() * addData.getInt("cooldown")), filter);
        }
    }

    @Override
    public void renderHolding(IGameInstance game, IAssetManager manager, IRenderer g, T item, ItemInstance instance, AbstractEntityPlayer player, float x, float y, float rotation, float scale, int filter, boolean mirrored) {
        super.renderHolding(game, manager, g, item, instance.copy().setAmount(-1), player, x, y, rotation, scale, filter, mirrored);
    }
}
