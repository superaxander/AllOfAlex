package alexanders.mods.aoa.item;

import alexanders.mods.aoa.entity.BombEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BombItem extends ItemBasic {
    public BombItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        if (layer == TileLayer.MAIN) {
            if (world.getState(layer, x, y).getTile().isAir()) {
                BombEntity e = new BombEntity(world);
                e.x = x + (x < 0 ? -0.5 : 0.5);
                e.y = y + 0.5;
                world.addEntity(e);
                player.getInv().remove(player.getSelectedSlot(), 1);
                return true;
            }
        }
        return super.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance);
    }
}
