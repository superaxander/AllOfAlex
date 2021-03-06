package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.Triplet;
import alexanders.mods.aoa.tile.FunnelTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class FunnelTileEntity extends TileEntity {

    public FunnelTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    private static Triplet<IInventory, List<Integer>, List<Integer>> getInventory(IWorld world, int x, int y, ItemInstance instance, Direction dir) {
        TileEntity te = world.getTileEntity(x, y);
        IFilteredInventory inventory = te.getTileInventory();
        if (inventory != null) {
            return new Triplet<>(inventory, inventory.getInputSlots(instance, dir), inventory.getOutputSlots(dir));
        }
        return null;
    }

    private static ItemInstance addExistingFirst(IInventory inventory, List<Integer> slots, ItemInstance instance, boolean simulate) {
        ItemInstance copy = instance.copy();

        for (int i = 0; i < 2; i++) {
            for (Integer slot : slots) {
                if (i == 1 || (inventory.get(slot) != null && inventory.get(slot).isEffectivelyEqual(instance))) {
                    copy = addToSlot(inventory, slot, copy, simulate);

                    if (copy == null) {
                        return null;
                    }
                }
            }
        }

        return copy;
    }

    private static ItemInstance addToSlot(IInventory inventory, int slot, ItemInstance instance, boolean simulate) {
        ItemInstance slotInst = inventory.get(slot);

        if (slotInst == null) {
            if (!simulate) {
                inventory.set(slot, instance);
            }
            return null;
        } else if (slotInst.isEffectivelyEqual(instance)) {
            int space = slotInst.getMaxAmount() - slotInst.getAmount();

            if (space >= instance.getAmount()) {
                if (!simulate) {
                    inventory.add(slot, instance.getAmount());
                }
                return null;
            } else {
                if (!simulate) {
                    inventory.add(slot, space);

                    instance.removeAmount(space);
                    if (instance.getAmount() <= 0) {
                        return null;
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (!getNet().isClient()) {
            List<AbstractEntityItem> entityList = world.getEntities(FunnelTile.BB.copy().add(x, y + 1).expand(1), AbstractEntityItem.class);
            for (AbstractEntityItem entityItem : entityList) {
                if (entityItem.canPickUp()) {
                    if (Util.distanceSq(entityItem.x, entityItem.y, this.x + .5, this.y + .5) <= .4) {
                        ItemInstance remaining = addExistingFirstConnectedTile(entityItem.getItem());

                        if (remaining == null) {
                            entityItem.kill();
                        } else {
                            entityItem.setItem(remaining);
                        }
                    } else {
                        double x = this.x + .5 - entityItem.x;
                        double y = this.y + .5 - entityItem.y;
                        double length = Util.distance(0, 0, x, y);
                        // System.out.println(length);
                        entityItem.motionX = 0.3 * (x / length);
                        entityItem.motionY = 0.3 * (y / length);
                    }
                }
            }
        }
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    private ItemInstance addExistingFirstConnectedTile(ItemInstance item) {
        Triplet<IInventory, List<Integer>, List<Integer>> triplet = null;
        switch (world.getState(x, y).get(FunnelTile.direction)) {
            case 0: // Down
                triplet = getInventory(world, x, y - 1, item, Direction.DOWN);
                break;
            case 1: // Right
                triplet = getInventory(world, x + 1, y, item, Direction.RIGHT);
                break;
            case 2: // Left
                triplet = getInventory(world, x - 1, y, item, Direction.LEFT);
                break;
        }
        if (triplet == null) return item;
        return addExistingFirst(triplet.a, triplet.b, item, false);
    }
}
