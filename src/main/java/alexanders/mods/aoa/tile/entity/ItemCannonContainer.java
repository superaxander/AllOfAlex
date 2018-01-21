package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.tile.entity.ItemCannonTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class ItemCannonContainer extends ItemContainer {

    public ItemCannonContainer(AbstractEntityPlayer player, ItemCannonTileEntity tileEntity) {
        super(player, player.getInv(), tileEntity.inventory);
        this.addSlot(new ContainerSlot(tileEntity.inventory, 0, 90, 0));
        this.addPlayerInventory(player, 20, 30);
    }

    @Override
    public IResourceName getName() {
        return createRes("item_cannon_container");
    }
}