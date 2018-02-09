package alexanders.mods.aoa.render;

import alexanders.mods.aoa.DataRecipe;
import alexanders.mods.aoa.net.DataCraftingPacket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class DataConstructComponent extends ComponentConstruct {
    public ItemInstance selectedIngredient;
    private final DataRecipe dataRecipe;
    private final AbstractEntityPlayer player;

    public DataConstructComponent(DataRecipe recipe, Gui gui, AbstractEntityPlayer player, boolean canConstruct) {
        super(gui, recipe, canConstruct);
        dataRecipe = recipe;
        this.player = player;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        DataCraftingPacket packet = new DataCraftingPacket(dataRecipe, selectedIngredient, player.getUniqueId());
        if(getNet().isClient()) {
            getNet().sendToServer(packet);
        }else {
            packet.handle(game, null);
        }
        return true;
    }
}
