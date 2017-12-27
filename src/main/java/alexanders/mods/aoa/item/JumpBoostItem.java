package alexanders.mods.aoa.item;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class JumpBoostItem extends ItemBasic { //TODO: Create a common class that takes an effect name as input
    public JumpBoostItem(IResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        DataSet additionalData = player.getAdditionalData();
        if (additionalData == null) {
            player.setAdditionalData(additionalData = new DataSet());
        }

        if (additionalData.getInt("effectJumpActive") <= 0) {
            IInventory inv = player.getInv();
            inv.remove(inv.getItemIndex(instance), 1);
            additionalData.addInt("effectJumpActive", 250);
        } else {
            return false;
        }

        return true;
    }
}
