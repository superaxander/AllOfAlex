package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ShadowSlot extends ContainerSlot {
    public ShadowSlot(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean canPlace(ItemInstance instance) {
        inventory.set(slot, instance.copy().setAmount(0));
        return false;
    }

    @Override
    public boolean canRemove(int amount) {
        inventory.set(slot, null);
        return false;
    }
}
