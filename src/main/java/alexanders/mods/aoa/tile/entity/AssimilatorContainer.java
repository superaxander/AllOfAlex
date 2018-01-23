package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.init.Tiles;
import alexanders.mods.aoa.render.RestrictedSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceAssimilator;

public class AssimilatorContainer extends ItemContainer {
    public AssimilatorContainer(AbstractEntityPlayer player, IInventory containedInventory) {
        super(player, player.getInv(), containedInventory);
        this.addSlot(new RestrictedSlot(containedInventory, 0, 40, 0, (slot, item) -> item.getItem() instanceof ItemTile && ((ItemTile) item.getItem()).getTile() == Tiles.assimilatedTile));
        this.addSlot(new RestrictedSlot(containedInventory, 1, 140, 0, (slot, item) -> item.getItem() instanceof ItemTile));
        this.addSlot(new RestrictedSlot(containedInventory, 2, 90, 20, (slot, item) -> false));
        this.addPlayerInventory(player, 20, 50);
    }

    @Override
    public IResourceName getName() {
        return resourceAssimilator;
    }
}
