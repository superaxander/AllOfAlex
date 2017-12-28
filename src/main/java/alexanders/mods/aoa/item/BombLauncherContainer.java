package alexanders.mods.aoa.item;

import alexanders.mods.aoa.render.RestrictedSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.bombLauncherResource;

public class BombLauncherContainer extends ItemContainer {
    public BombLauncherContainer(AbstractEntityPlayer player, IInventory... containedInventories) {
        super(player, containedInventories);
        this.addSlot(new RestrictedSlot(containedInventories[1], 0, 90, 0, (item) -> item.getItem() instanceof BombItem));
        this.addPlayerInventory(player, 20, 30);
    }

    @Override
    public IResourceName getName() {
        return bombLauncherResource;
    }
}
