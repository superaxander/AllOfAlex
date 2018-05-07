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

import static alexanders.mods.aoa.init.Resources.EFFECT_JUMP_ACTIVE;

public class JumpBoostItem extends ItemBasic { //TODO: Create a common class that takes an effect name as input
    public JumpBoostItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        ModBasedDataSet additionalData = player.getAdditionalData();
        if (additionalData == null) {
            player.setAdditionalData(additionalData = new ModBasedDataSet());
        }

        if (additionalData.getInt(EFFECT_JUMP_ACTIVE) <= 0) {
            IInventory inv = player.getInv();
            inv.remove(inv.getItemIndex(instance), 1);
            additionalData.addInt(EFFECT_JUMP_ACTIVE, 250);
        } else {
            return false;
        }

        return true;
    }
}
