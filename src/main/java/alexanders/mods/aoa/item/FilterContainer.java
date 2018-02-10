package alexanders.mods.aoa.item;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceFilterContainer;

public class FilterContainer extends ItemContainer {
    public FilterContainer(AbstractEntityPlayer player, Inventory... inventories) {
        super(player, inventories);
        this.addSlotGrid(inventories[1], 0, 8, 60, 0, 3);
        this.addPlayerInventory(player, 0, 50);
    }

    @Override
    public IResourceName getName() {
        return resourceFilterContainer;
    }
}
