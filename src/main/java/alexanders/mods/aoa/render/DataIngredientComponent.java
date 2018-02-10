package alexanders.mods.aoa.render;

import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;

import java.util.Collections;

public class DataIngredientComponent extends ComponentIngredient {
    private final DataRecipe recipe;
    private final ItemInstance in;
    
    public DataIngredientComponent(DataRecipe recipe, Gui gui, ItemInstance in) {
        super(gui, true, Collections.singletonList(in));
        this.recipe = recipe;
        this.in = in;
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        super.renderOverlay(game, manager, g, x, y);
        if(ItemInstance.compare(recipe.currentComponent.selectedIngredient, in, true, true, true)) {
            g.addEmptyRect(x, y, 14, 14, Colors.WHITE);
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if(isMouseOver(game)) {
            recipe.currentComponent.selectedIngredient = in;
            return true;
        }
        return false;
    }
}
