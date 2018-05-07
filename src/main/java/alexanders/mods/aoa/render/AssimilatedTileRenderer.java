package alexanders.mods.aoa.render;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.tile.AssimilatedTile;
import alexanders.mods.aoa.tile.entity.AssimilatedTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static alexanders.mods.aoa.tile.ColourableTile.COLOUR;

public class AssimilatedTileRenderer extends ColourableTileRenderer<AssimilatedTile> {
    public static final ResourceName TILE_NAME = createRes("tileName");
    
    public AssimilatedTileRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, AssimilatedTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        TileEntity t = world.getTileEntity(layer, x, y);
        if (t instanceof AssimilatedTileEntity && ((AssimilatedTileEntity) t).tileName != null) {
            AssimilatedTileEntity te = (AssimilatedTileEntity) t;
            manager.getTexture(te.tileName.addPrefix("tiles.")).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light, state.get(COLOUR).colour);
        } else {
            super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        }
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, AssimilatedTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        ResourceName name;
        if (instance.hasAdditionalData() && (name = new ResourceName(instance.getAdditionalData().getString(TILE_NAME))) != null) {//TODO: Fix this broken logic
            manager.getTexture(name.addPrefix("tiles.")).draw(x, y, scale, scale, Colors.multiply(Colours.get(instance.getMeta()).colour, filter));
        } else {
            super.renderItem(game, manager, g, tile, instance, x, y, scale, filter);
        }
    }
}
