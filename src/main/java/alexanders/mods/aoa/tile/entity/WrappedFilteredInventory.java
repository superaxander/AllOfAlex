package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.Direction;

import java.util.List;
import java.util.function.BiConsumer;

public class WrappedFilteredInventory implements IFilteredInventory {
    private final List<Integer> inputSlots;
    private final List<Integer> outputSlots;
    private final IInventory inventory;

    public WrappedFilteredInventory(IInventory inventory, List<Integer> inputSlots, List<Integer> outputSlots) {
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.inventory = inventory;
    }

    @Override
    public void set(int id, ItemInstance instance) {
        inventory.set(id, instance);
    }

    @Override
    public ItemInstance add(int id, int amount) {
        return inventory.add(id, amount);
    }

    @Override
    public ItemInstance remove(int id, int amount) {
        return inventory.remove(id, amount);
    }

    @Override
    public ItemInstance get(int id) {
        return inventory.get(id);
    }

    @Override
    public int getSlotAmount() {
        return inventory.getSlotAmount();
    }

    @Override
    public void notifyChange(int slot) {
        inventory.notifyChange(slot);
    }

    @Override
    public void addChangeCallback(BiConsumer<IInventory, Integer> callback) {
        inventory.addChangeCallback(callback);
    }

    @Override
    public void removeChangeCallback(BiConsumer<IInventory, Integer> callback) {
        inventory.removeChangeCallback(callback);
    }

    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate) {
        return inventory.addToSlot(slot, instance, simulate);
    }


    @Override
    public List<Integer> getInputSlots(ItemInstance instance, Direction dir) {
        return inputSlots;
    }

    @Override
    public List<Integer> getOutputSlots(Direction dir) {
        return outputSlots;
    }
}
