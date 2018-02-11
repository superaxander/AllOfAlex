package alexanders.mods.aoa.item;

import alexanders.mods.aoa.render.ShadowSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceFilterContainer;

public class FilterContainer extends ItemContainer {
    public FilterContainer(AbstractEntityPlayer player, IInventory... inventories) {
        super(player, inventories);
        this.addShadowSlotGrid(inventories[1], 0, 9, 51, 0, 3);
        this.addPlayerInventory(player, 0, 55);
    }
    
    protected void addShadowSlotGrid(IInventory inventory, int start, int end, int xStart, int yStart, int width){
        int x = xStart;
        int y = yStart;
        for(int i = start; i < end; i++){
            this.addSlot(new ShadowSlot(inventory, i, x, y));

            x += 17;
            if((i+1)%width == 0){
                y += 17;
                x = xStart;
            }
        }
    }
    @Override
    public IResourceName getName() {
        return resourceFilterContainer;
    }
}
