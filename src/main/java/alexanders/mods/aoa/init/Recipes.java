package alexanders.mods.aoa.init;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class Recipes {
    public static DataRecipe[] dataRecipes;

    public static void init() {
        IResourceRegistry reg = RockBottomAPI.getResourceRegistry();
        reg.addResources("stone", GameContent.TILE_STONE);
        reg.addResources("log", GameContent.TILE_LOG);
        reg.addResources("sand", GameContent.TILE_SAND);
        new BasicRecipe(new ItemInstance(Items.shearsItem), new ResUseInfo("stone", 6), new ResUseInfo("log", 3)).registerManual();

        Colours[] values = Colours.values();
        ItemInstance plain = new ItemInstance(Tiles.plain, 1, 0);
        new BasicRecipe(createRes("plain_sand_" + values[0]), plain, new ResUseInfo("sand", 4)).registerManual();
        addColourable(Tiles.plain, "plain", values);
        addColourable(Tiles.itemConduit, "item_conduit", values);
        addColourable(Items.paintBombItem, "paint_bomb", values);

        ItemInstance white = new ItemInstance(Tiles.assimilatedTile, 1, 0);
        for (int i = 1; i < values.length; i++) {
            ItemInstance in = new ItemInstance(Tiles.assimilatedTile, 1, i - 1);
            ItemInstance out = new ItemInstance(Tiles.assimilatedTile, 1, i);
            new DataRecipe(createRes("assimilated_white_" + values[i - 1] + "_" + values[i]), out, in).registerManual();
        }
        ItemInstance in = new ItemInstance(Tiles.assimilatedTile, 1, values.length - 1);
        new DataRecipe(createRes("assimilated_white_" + values[values.length - 1] + "_" + values[0]), white, in).registerManual();
    }

    private static void addColourable(Tile tile, String prefix, Colours[] values) {
        ItemInstance white = new ItemInstance(tile, 1, 0);
        for (int i = 1; i < values.length; i++) {
            ItemInstance in = new ItemInstance(tile, 1, i - 1);
            ItemInstance out = new ItemInstance(tile, 1, i);
            new BasicRecipe(createRes(prefix + "_" + values[i - 1] + "_" + values[i]), out, new ItemUseInfo(in)).registerManual();
        }
        ItemInstance in = new ItemInstance(tile, 1, values.length - 1);
        new BasicRecipe(createRes(prefix + "_" + values[values.length - 1] + "_" + values[0]), white, new ItemUseInfo(in)).registerManual();
    }

    private static void addColourable(Item item, String prefix, Colours[] values) {
        ItemInstance white = new ItemInstance(item, 1, 0);
        for (int i = 1; i < values.length; i++) {
            ItemInstance in = new ItemInstance(item, 1, i - 1);
            ItemInstance out = new ItemInstance(item, 1, i);
            new BasicRecipe(createRes(prefix + "_" + values[i - 1] + "_" + values[i]), out, new ItemUseInfo(in)).registerManual();
        }
        ItemInstance in = new ItemInstance(item, 1, values.length - 1);
        new BasicRecipe(createRes(prefix + "_" + values[values.length - 1] + "_" + values[0]), white, new ItemUseInfo(in)).registerManual();
    }
}
