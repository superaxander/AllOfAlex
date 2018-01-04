package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class ItemCannonTileEntity extends TileEntity implements IInventoryHolder {
    public Inventory inventory = new Inventory(1);
    public int cooldown = 0;
    private boolean dirty = false;

    public ItemCannonTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        inventory.addChangeCallback((a, b) -> dirty = true);
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        if (forSync)
            dirty = false;
        set.addInt("cooldown", cooldown);
        DataSet inv = new DataSet();
        inventory.save(inv);
        set.addDataSet("inv", inv);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        if (forSync)
            dirty = false;
        cooldown = set.getInt("cooldown");
        DataSet inv = set.getDataSet("inv");
        inventory.load(inv);
    }

    @Override
    protected boolean needsSync() {
        return dirty;
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (getNet().isServer() || !getNet().isActive()) {
            if (cooldown <= 0) {
                ItemInstance ii = inventory.get(0);
                if (ii != null) {
                    ItemInstance itemInstance = ii.copy();
                    if (itemInstance.getAmount() > 0) {
                        inventory.remove(0, 1);
                        EntityItem itemEntity = new EntityItem(world, itemInstance.setAmount(1));
                        int degrees = world.getState(x, y).get(ItemCannonTile.rotation) * 5;
                        itemEntity.x = x + .75;
                        itemEntity.y = y + .75;

                        itemEntity.motionX = Math.cos(Math.toRadians(degrees)) / 1.5;
                        itemEntity.motionY = Math.sin(Math.toRadians(degrees)) / 1.5;
                        world.addEntity(itemEntity);
                        cooldown = 200;
                        dirty = true;
                    }
                }
            } else {
                cooldown--;
                dirty = true;
            }
        }
    }

    @Override
    public IInventory getInventory() {
        return inventory;
    }

    @Override
    public List<Integer> getInputSlots(ItemInstance instance, Direction dir) {
        return Collections.singletonList(0);
    }

    @Override
    public List<Integer> getOutputSlots(Direction dir) {
        return Collections.emptyList();
    }
}

