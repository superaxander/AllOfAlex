package alexanders.mods.aoa.item;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.EFFECT_SPEED_ACTIVE;

public class SpeedBoostItem extends ItemBasic {
    public SpeedBoostItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        ModBasedDataSet additionalData = player.getAdditionalData();
        if (additionalData == null) {
            player.setAdditionalData(additionalData = new ModBasedDataSet());
        }
        if (additionalData.getInt(EFFECT_SPEED_ACTIVE) <= 0) {
            System.out.println("activated");
            IInventory inv = player.getInv();
            inv.remove(inv.getItemIndex(instance), 1);
            additionalData.addInt(EFFECT_SPEED_ACTIVE, 250);
        } else {
            return super.onInteractWith(world, x, y, layer, mouseX, mouseY, player, instance);
        }

        return true;
    }
}
