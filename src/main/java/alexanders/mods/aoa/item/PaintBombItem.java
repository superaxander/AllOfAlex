package alexanders.mods.aoa.item;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.entity.PaintBombEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class PaintBombItem extends BombItem {
    public PaintBombItem(ResourceName name) {
        super(name);
    }

    @Override
    public ResourceName getUnlocalizedName(ItemInstance instance) {
        return super.getUnlocalizedName(instance).addSuffix("." + Colours.get(instance.getMeta()));
    }

    @Override
    public int getHighestPossibleMeta() {
        return Colours.values().length;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        if (layer == TileLayer.MAIN) {
            if (world.getState(layer, x, y).getTile().isAir()) {
                PaintBombEntity e = new PaintBombEntity(world, Colours.get(instance.getMeta()));
                e.x = x + (x < 0 ? 0.5 : -0.5);
                e.y = y + 0.5;
                world.addEntity(e);
                player.getInv().remove(player.getSelectedSlot(), 1);
                return true;
            }
        }
        return super.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance);
    }
}
