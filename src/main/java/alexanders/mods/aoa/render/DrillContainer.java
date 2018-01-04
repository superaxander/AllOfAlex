package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.entity.TileEntityDrill;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class DrillContainer extends ItemContainer {
    public DrillContainer(AbstractEntityPlayer player, TileEntityDrill tile, IInventory... containedInventories) {
        super(player, containedInventories);
        this.addSlot(new RestrictedSlot(tile.fuelInventory, 0, 90, 0, (slot, instance) -> instance.getItem() == GameContent.TILE_LOG.getItem() || instance.getItem() == GameContent.TILE_COAL.getItem())); //TODO: Change this once fuel is reimplemented
        this.addSlotGrid(tile.inventory, 0, tile.inventorySize, 0, 30, 11); // @BUG: Logically this should be 10
        this.addPlayerInventory(player, 20, 80);
    }

    @Override
    public IResourceName getName() {
        return createRes("drill_container");
    }
}
