package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ItemFilter {
    public final ItemInstance[] itemInstances;
    public final boolean isBlackList;
    public final boolean ignoreData;
    public final boolean ignoreMeta;

    public ItemFilter(ItemInstance[] itemInstances, boolean isBlackList, boolean ignoreData, boolean ignoreMeta) {
        this.itemInstances = itemInstances;
        this.isBlackList = isBlackList;
        this.ignoreData = ignoreData;
        this.ignoreMeta = ignoreMeta;
    }

    public ItemFilter(Inventory inventory, boolean isBlackList, boolean ignoreData, boolean ignoreMeta) {
        this(getSlots(inventory), isBlackList, ignoreData, ignoreMeta);
    }

    private static ItemInstance[] getSlots(Inventory inventory) {
        ItemInstance[] slots = new ItemInstance[inventory.getSlotAmount()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = inventory.get(i);
        }
        return slots;
    }

    public boolean contains(ItemInstance item) {
        for (ItemInstance instance : itemInstances) {
            if (ItemInstance.compare(item, instance, true, !ignoreMeta, !ignoreData))
                return true;
        }
        return false;
    }
}
