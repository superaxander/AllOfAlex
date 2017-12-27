package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class Recipes {
    public static void init() {
        GameContent.TILE_STONE.addResource("stone");
        GameContent.TILE_LOG.addResource("log");
        new BasicRecipe(new ItemInstance(Items.shears), new ResUseInfo("stone", 6), new ResUseInfo("log", 3)).registerManual();
    }
}
