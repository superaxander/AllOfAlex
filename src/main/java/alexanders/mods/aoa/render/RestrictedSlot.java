package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.BiPredicate;

public class RestrictedSlot extends ContainerSlot {
    private final BiPredicate<RestrictedSlot, ItemInstance> predicate;

    public RestrictedSlot(IInventory inventory, int slot, int x, int y, BiPredicate<RestrictedSlot, ItemInstance> predicate) {
        super(inventory, slot, x, y);
        this.predicate = predicate;
    }

    @Override
    public boolean canPlace(ItemInstance instance) {
        return predicate.test(this, instance);
    }
}
