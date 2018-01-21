package alexanders.mods.aoa;

import alexanders.mods.aoa.render.DataRecipeComponent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.Collections;
import java.util.List;

public class DataRecipe extends BasicRecipe {
    private final IResourceName name;
    public final ItemInstance output;
    public final ItemInstance input;

    public DataRecipe(IResourceName name, ItemInstance output, ItemInstance input) {
        super(name, output, new ItemUseInfo(input));
        this.name = name;
        this.output = output;
        this.input = input;
    }

    @Override
    public List<IUseInfo> getActualInputs(IInventory inventory) {
        int amount = inventory.getSlotAmount();
        for (int i = 0; i < amount; i++) {
            if(ItemInstance.compare(inventory.get(i), input, true, true, false))
                return Collections.singletonList(new ItemUseInfo(inventory.get(i)));
        }
        return super.getActualInputs(inventory);
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player) {
        return true;
    }

    @Override
    public boolean shouldDisplayIngredient(AbstractEntityPlayer player, IUseInfo info) {
        return true;
    }

    @Override
    public boolean shouldDisplayOutput(AbstractEntityPlayer player, ItemInstance output) {
        return true;
    }

    @Override
    public IResourceName getName() {
        return name;
    }

    @Override
    public GuiComponent getCustomComponent(IInventory inventory) {
        return new DataRecipeComponent(RockBottomAPI.getGame().getGuiManager().getGui(), inventory, this, RockBottomAPI.getGame().getPlayer());
    }
}
