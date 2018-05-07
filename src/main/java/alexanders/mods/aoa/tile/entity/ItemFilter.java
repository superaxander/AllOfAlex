package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ItemFilter {
    public final ItemInstance[] itemInstances;
    public boolean isBlacklist;
    public boolean ignoreData;
    public boolean ignoreMeta;

    public ItemFilter(ItemInstance[] itemInstances, boolean isBlacklist, boolean ignoreData, boolean ignoreMeta) {
        this.itemInstances = itemInstances;
        this.isBlacklist = isBlacklist;
        this.ignoreData = ignoreData;
        this.ignoreMeta = ignoreMeta;
    }

    public ItemFilter(Inventory inventory, boolean isBlacklist, boolean ignoreData, boolean ignoreMeta) {
        this(getSlots(inventory), isBlacklist, ignoreData, ignoreMeta);
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
            if (ItemInstance.compare(item, instance, true, false, !ignoreMeta, !ignoreData)) return true;
        }
        return false;
    }

    public void save(DataSet set) {
        for (int i = 0; i < this.itemInstances.length; i++) {
            ItemInstance instance = this.itemInstances[i];

            if (instance != null) {
                DataSet subset = new DataSet();
                instance.save(subset);
                set.addDataSet("item_" + i, subset);
            }
        }
        set.addBoolean("isBlacklist", isBlacklist);
        set.addBoolean("ignoreData", ignoreData);
        set.addBoolean("ignoreMeta", ignoreMeta);
    }

    public void load(DataSet set) {
        for (int i = 0; i < this.itemInstances.length; i++) {
            DataSet subset = set.getDataSet("item_" + i);
            if (!subset.isEmpty()) {
                this.itemInstances[i] = ItemInstance.load(subset);
            } else {
                this.itemInstances[i] = null;
            }
        }
        isBlacklist = set.getBoolean("isBlacklist");
        ignoreData = set.getBoolean("ignoreData");
        ignoreMeta = set.getBoolean("ignoreMeta");
    }
}
