package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.item.BombItem;
import alexanders.mods.aoa.render.RestrictedSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceBombCannonContainer;

public class BombCannonContainer extends ItemContainer {
    public BombCannonContainer(AbstractEntityPlayer player, BombCannonTileEntity te) {
        super(player, player.getInv(), te.inventory);
        this.addSlot(new RestrictedSlot(te.inventory, 0, 90, 0, ((restrictedSlot, itemInstance) -> itemInstance.getItem() instanceof BombItem)));
        this.addPlayerInventory(player, 20, 30);
    }

    @Override
    public IResourceName getName() {
        return resourceBombCannonContainer;
    }
}
