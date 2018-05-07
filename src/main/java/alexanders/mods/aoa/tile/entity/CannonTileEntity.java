package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.BasicFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public abstract class CannonTileEntity extends TileEntity {
    public BasicFilteredInventory inventory = new BasicFilteredInventory(1, Collections.singletonList(0), Collections.emptyList());
    public int cooldown = 0;
    public int maxCooldown = 200;
    private boolean dirty = false;

    public CannonTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        inventory.addChangeCallback((a, b) -> dirty = true);
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        if (forSync) dirty = false;
        set.addInt("cooldown", cooldown);
        DataSet inv = new DataSet();
        inventory.save(inv);
        set.addDataSet("inv", inv);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        if (forSync) dirty = false;
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
                        int degrees = world.getState(x, y).get(ItemCannonTile.rotation) * 5;
                        Entity entity = createEntity(world, itemInstance, x + .75, y + .75, Math.cos(Math.toRadians(degrees)) / 1.5, Math.sin(Math.toRadians(degrees)) / 1.5);
                        world.addEntity(entity);
                        cooldown = maxCooldown;
                        dirty = true;
                    }
                }
            } else {
                cooldown--;
                dirty = true;
            }
        }
    }

    protected abstract Entity createEntity(IWorld world, ItemInstance itemInstance, double x, double y, double motionX, double motionY);

    @Override
    public IFilteredInventory getTileInventory() {
        return inventory;
    }

    @Override
    public boolean doesTick() {
        return true;
    }
}

