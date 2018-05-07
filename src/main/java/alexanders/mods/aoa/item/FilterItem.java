package alexanders.mods.aoa.item;

import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.init.Tiles;
import alexanders.mods.aoa.render.FilterGui;
import alexanders.mods.aoa.tile.entity.ItemConduitTileEntity;
import alexanders.mods.aoa.tile.entity.ItemFilter;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class FilterItem extends ItemBasic {
    private static final ResourceName INVENTORY = createRes("inventory");
    private static final ResourceName IS_BLACKLIST = createRes("isBlacklist");
    private static final ResourceName IGNORE_DATA = createRes("ignoreData");
    private static final ResourceName IGNORE_META = createRes("ignoreMeta");
    
    public FilterItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        ModBasedDataSet addData = instance.getOrCreateAdditionalData();
        DataSet inventory = addData.getDataSet(INVENTORY);
        Inventory inv = new Inventory(9);
        if (inventory != null) inv.load(inventory);

        if (world.getState(layer, x, y).getTile() == Tiles.itemConduit) {
            if (!getNet().isServer() && Keys.KEY_REMOVE_FILTER.isDown()) return true;
            ItemConduitTileEntity te = world.getTileEntity(layer, x, y, ItemConduitTileEntity.class);
            te.filter = new ItemFilter(inv, addData.getBoolean(IS_BLACKLIST), addData.getBoolean(IGNORE_DATA), addData.getBoolean(IGNORE_META));
        } else {
            inv.addChangeCallback((i, index) -> {
                DataSet newInventory = new DataSet();
                inv.save(newInventory);
                addData.addDataSet(INVENTORY, newInventory);
            });
            player.openGuiContainer(new FilterGui(player, addData), new FilterContainer(player, player.getInv(), inv));
        }
        return true;
    }

    @Override
    public boolean isDataSensitive(ItemInstance instance) {
        return true;
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
