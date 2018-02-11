package alexanders.mods.aoa.render;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.tile.ConduitConnections;
import alexanders.mods.aoa.tile.ItemConduitTile;
import alexanders.mods.aoa.tile.entity.ItemConduitTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.tile.ColourableTile.COLOUR;
import static alexanders.mods.aoa.tile.ItemConduitTile.CONNECTIONS;

public class ItemConduitTileRender extends ColourableTileRenderer<ItemConduitTile> {
    public ItemConduitTileRender(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, ItemConduitTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        ConduitConnections connections = state.get(CONNECTIONS);
        IResourceName t = world.getTileEntity(layer, x, y, ItemConduitTileEntity.class).filter == null ? texture : texture.addSuffix(".filtered");
        RotateableTexture tex = new RotateableTexture(manager.getTexture(t.addSuffix("." + connections.suffix)).getPositionalVariation(x, y));
        //g.rotate(connections.rotation);
        tex.setRotation(connections.rotation);
        tex.setRotationCenter(scale * .5f, scale * .5f);
        //tex.setRotationCenter(scale * .5f, scale * .5f);
        tex.draw(renderX, renderY, scale, scale, light, state.get(COLOUR).colour);
        //g.rotate(-connections.rotation);
        ItemConduitTileEntity te = (ItemConduitTileEntity) world.getTileEntity(layer, x, y);
        int color;
        switch (te.mode) {
            case 0:
                color = Colors.TRANSPARENT;
                break;
            case 1:
                color = Colours.BLUE.colour;
                break;
            case 2:
                color = Colors.ORANGE;
                break;
            case 3:
                color = Colors.GREEN;
                break;
            default:
                color = Colors.GREEN;
                break;
        }
        g.addFilledRect(renderX + scale / 12 * 5, renderY + scale / 12 * 5, 2 * scale / 12, 2 * scale / 12, color);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, ItemConduitTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        ITexture tex = manager.getTexture(texture.addSuffix("." + ConduitConnections.NONE.suffix));
        g.rotate(ConduitConnections.NONE.rotation);
        tex.draw(x, y, scale, scale, Colors.multiply(Colours.get(instance.getMeta()).colour, filter));
        g.rotate(-ConduitConnections.NONE.rotation);
    }
}
