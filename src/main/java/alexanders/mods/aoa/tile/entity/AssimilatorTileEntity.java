package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.init.Tiles;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class AssimilatorTileEntity extends TileEntity {
    public Inventory inventory;
    public int progress = 0;
    public ItemInstance firstItem;
    public ItemInstance secondItem;
    private boolean changed = false;

    public AssimilatorTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        inventory = new Inventory(3);
    }

    @Override
    protected boolean needsSync() {
        return changed || super.needsSync();
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (progress > 0) {
            progress--;
            changed = true;
        } else if (progress == 0 && inventory.get(2) == null) {
            if (firstItem != null && secondItem != null) {
                firstItem.getOrCreateAdditionalData().addString("tileName", secondItem.getItem().getName().toString());
                inventory.set(2, firstItem);
                firstItem = null;
                secondItem = null;
                changed = true;
            } else if ((firstItem = inventory.get(0)) != null && firstItem.getItem() == Tiles.assimilatedTile.getItem() && (secondItem = inventory.get(1)) != null) {
                changed = true;
                progress = 200;
                firstItem = firstItem.copy().setAmount(1);
                secondItem = secondItem.copy();
                // Remove items
                inventory.remove(0, 1);
                inventory.remove(1, 1);
            }
        }
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        if (forSync)
            changed = false;
        DataSet inv = new DataSet();
        inventory.save(inv);
        set.addDataSet("inventory", inv);
        set.addInt("progress", progress);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        if (forSync)
            changed = false; // This is probably unneeded
        inventory.load(set.getDataSet("inventory"));
        progress = set.getInt("progress");
    }

    public float getProgressBar() {
        return (200 - progress) / 200f;
    }
}
