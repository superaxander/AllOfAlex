package alexanders.mods.aoa.render;

import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentScrollMenu;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class DataRecipeComponent extends GuiComponent {
    private static final IResourceName constructs = RockBottomAPI.createInternalRes("info.constructs");
    private static final IResourceName uses = RockBottomAPI.createInternalRes("info.uses");

    private final Gui parent;
    private final IInventory inventory;
    private final DataRecipe recipe;
    private final AbstractEntityPlayer player;
    private boolean pressed;

    public DataRecipeComponent(Gui parent, IInventory inventory, DataRecipe recipe, AbstractEntityPlayer player) {
        super(parent, 0, 0, -2, -2);
        this.parent = parent;
        this.inventory = inventory;
        this.recipe = recipe;
        this.player = player;
        updateMenu();
    }

    private void updateMenu() {
        List<GuiComponent> parentComponents = parent.getComponents();
        Stream<GuiComponent> menus = parentComponents.stream().filter(c -> c instanceof ComponentScrollMenu);
        ComponentScrollMenu menu = (ComponentScrollMenu) menus.toArray()[0];
        // Add our own recipes
        List<ItemInstance> recipes = new ArrayList<>();
        ItemInstance input = recipe.input;
        ItemInstance basicOutput = recipe.output;
        int invSize = inventory.getSlotAmount();
        for (int i = 0; i < invSize; i++) {
            ItemInstance instance = inventory.get(i);
            if (instance != null) {
                if (input.getItem() == instance.getItem() && input.getMeta() == instance.getMeta()) {
                    ItemInstance in = instance.copy().setAmount(1);
                    if (!recipes.contains(in)) {
                        recipes.add(in);
                        ItemInstance output = basicOutput.copy();
                        output.setAdditionalData(instance.getAdditionalData());
                        menu.add(new DataRecipeButton(parent, 0, 0, 16, 16, inventory, in, output));
                    }
                }
            }
        }
        menu.organize();
    }

    @Override
    public boolean isMouseOver(IGameInstance game) {
        return false;
    }

    @Override
    public IResourceName getName() {
        return createRes("assimilated_tile_crafting");
    }
}
