package alexanders.mods.aoa.render;

import alexanders.mods.aoa.DataRecipe;
import alexanders.mods.aoa.net.DataCraftingPacket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class DataConstructComponent extends ComponentConstruct {
    private final DataRecipe dataRecipe;
    private final AbstractEntityPlayer player;
    public ItemInstance selectedIngredient;

    public DataConstructComponent(DataRecipe recipe, Gui gui, AbstractEntityPlayer player, boolean canConstruct) {
        super(gui, recipe, canConstruct);
        dataRecipe = recipe;
        this.player = player;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (selectedIngredient == null) super.render(game, manager, g, x, y);
        else {
            ItemInstance outItem = dataRecipe.output.copy();
            outItem.setAdditionalData(selectedIngredient.getAdditionalData());
            g.renderItemInGui(game, manager, outItem, x + 2, y + 2, 2.6F, Colors.WHITE);
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (isMouseOver(game)) {
            if (selectedIngredient != null) {
                DataCraftingPacket packet = new DataCraftingPacket(dataRecipe, selectedIngredient, player.getUniqueId());
                if (getNet().isClient()) {
                    getNet().sendToServer(packet);
                } else {
                    packet.handle(game, null);
                }
            }
            return true;
        }
        return false;
    }
}
