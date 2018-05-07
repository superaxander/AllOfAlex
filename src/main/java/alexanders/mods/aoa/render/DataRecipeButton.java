package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;

public class DataRecipeButton extends ComponentButton {
    private static final int IMPOSSIBLE = Colors.rgb(1.0F, 1.0F, 1.0F, 0.5F);
    private static final ResourceName constructs = ResourceName.intern("info.constructs");
    private static final ResourceName uses = ResourceName.intern("info.uses");
    private final IInventory inventory;
    private final ItemInstance input;
    private final ItemInstance output;
    private boolean possible = true;

    public DataRecipeButton(Gui gui, int x, int y, int width, int height, IInventory inventory, ItemInstance input, ItemInstance output) {
        super(gui, x, y, width, height, null, null);
        this.inventory = inventory;
        this.input = input;
        this.output = output;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        super.render(game, manager, g, x, y);
        g.renderItemInGui(game, manager, output, x + 2f, y + 2f, 1f, possible ? -1 : IMPOSSIBLE);
    }

    @Override
    public boolean onPressed(IGameInstance game) {
        int index = this.inventory.getItemIndex(input);
        int emptyIndex = findEmpty(this.inventory);
        System.out.println(emptyIndex);
        if (index != -1 && emptyIndex != -1) {
            this.inventory.remove(index, 1);
            this.inventory.addToSlot(emptyIndex, output.copy(), false);
            return true;
        }
        return false;
    }

    private int findEmpty(IInventory inventory) {
        int empty = -1;
        for (int i = 0; i < inventory.getSlotAmount(); i++) {
            if (inventory.get(i) == null) {
                if (empty == -1) empty = i;
            } else if (ItemInstance.compare(inventory.get(i), output, true, false, true, true) && inventory.get(i).fitsAmount(output.getAmount())) {
                return i;
            }
        }
        return empty;
    }

    protected final String[] getHover() {
        IGameInstance game = RockBottomAPI.getGame();
        IAssetManager manager = game.getAssetManager();
        ArrayList<String> text = new ArrayList<>();

        text.add(manager.localize(constructs) + ":");

        ItemInstance var6 = output;
        text.add(FormattingCode.YELLOW + " " + var6.getDisplayName() + " x" + var6.getAmount());
        text.add(manager.localize(uses) + ":");

        FormattingCode formattingCode;
        if (!this.inventory.containsItem(input)) {
            formattingCode = FormattingCode.RED;
        } else {
            formattingCode = FormattingCode.GREEN;
        }

        text.add(formattingCode + " " + input.getDisplayName() + " x" + input.getAmount());

        return text.toArray(new String[text.size()]);
    }
}
