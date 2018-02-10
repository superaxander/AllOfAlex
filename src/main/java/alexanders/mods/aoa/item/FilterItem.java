package alexanders.mods.aoa.item;

import alexanders.mods.aoa.render.FilterGui;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class FilterItem extends ItemBasic {
    public FilterItem(IResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        DataSet inventory = instance.getOrCreateAdditionalData().getDataSet("inventory");
        Inventory inv = new Inventory(9);;
        if(inventory != null)
            inv.load(inventory);
        player.openGuiContainer(new FilterGui(player), new FilterContainer(player, player.getInv(), inv));
        return true;
    }
}
