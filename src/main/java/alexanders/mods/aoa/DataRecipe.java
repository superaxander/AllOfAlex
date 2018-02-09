package alexanders.mods.aoa;

import alexanders.mods.aoa.render.DataConstructComponent;
import alexanders.mods.aoa.render.DataIngredientComponent;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class DataRecipe extends BasicRecipe {
    public final ItemInstance output;
    public final ItemInstance input;
    private final IResourceName name;

    public DataConstructComponent currentComponent = null;

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
            if (ItemInstance.compare(inventory.get(i), input, true, true, false))
                return Collections.singletonList(new ItemUseInfo(inventory.get(i)));
        }
        return super.getActualInputs(inventory);
    }
    
    @Override
    public ComponentConstruct getConstructButton(Gui gui, AbstractEntityPlayer player, boolean canConstruct) {
        return currentComponent = new DataConstructComponent(this, gui, player, canConstruct);
    }

    @Override
    public List<ComponentIngredient> getIngredientButtons(Gui gui, AbstractEntityPlayer player) {
        ArrayList<ComponentIngredient> components = new ArrayList<>();
        Inventory inventory = player.getInv();
        int invSize = inventory.getSlotAmount();
        for (int i = 0; i < invSize; i++) {
            ItemInstance instance = inventory.get(i);
            if (instance != null) {
                if (input.getItem() == instance.getItem() && input.getMeta() == instance.getMeta()) {
                    ItemInstance in = instance.copy().setAmount(1);
                    components.add(new DataIngredientComponent(this, gui, in));
                }
            }
        }
        return components;
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player) {
        return true;
    }

    @Override
    public IResourceName getName() {
        return name;
    }
}
