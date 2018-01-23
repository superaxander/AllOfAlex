package alexanders.mods.aoa.init;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.DataRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class Recipes {
    public static DataRecipe[] dataRecipes;

    public static void init() {
        GameContent.TILE_STONE.addResource("stone");
        GameContent.TILE_LOG.addResource("log");
        GameContent.TILE_SAND.addResource("sand");
        new BasicRecipe(new ItemInstance(Items.shearsItem), new ResUseInfo("stone", 6), new ResUseInfo("log", 3)).registerManual();

        Colours[] values = Colours.values();
        ItemInstance plain = new ItemInstance(Tiles.plain, 1, 0);
        new BasicRecipe(createRes("plain_sand_" + values[0]), plain, new ResUseInfo("sand", 4)).registerManual();
        addColourable(Tiles.plain, "plain", values);
        addColourable(Tiles.itemConduit, "item_conduit", values);

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
}
