package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.ItemCannonTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemCannonRenderer extends DefaultTileRenderer<ItemCannonTile> {
    private RotateableTexture t;

    public ItemCannonRenderer(IResourceName name) {
        super(name);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, ItemCannonTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        if (t == null)
            t = new RotateableTexture(manager.getTexture(texture.addSuffix(".head")));
        //t.setRotationCenter(scale * 0.5f, scale / 2);
        //g.rotate(360 - state.get(ItemCannonTile.rotation) * 5);
        //g.translate(centerX, centerY);
        //g.translate(centerX + (((t.getRenderWidth() - t.getRenderHeight())/2f)* (float)Math.sin(rot) - t.getRenderWidth()/2), centerY - (((t.getRenderWidth() - t.getRenderHeight())/2f)* (float)Math.cos(rot) - t.getRenderHeight()/2));
        t.setRotation(360 - state.get(ItemCannonTile.rotation) * 5);
        t.setRotationCenter(scale * .5f, scale / 2);
        t.draw(renderX, renderY - scale / 4, scale * 2, scale, light);
        //t.draw(renderX + rotateX(centerX, centerY, 0, 0, rot),
        //        renderY + rotateY(centerX, centerY, 0, 0, rot),
        //        renderX + rotateX(centerX, centerY, 0, scale, rot),
        //         renderY +rotateY(centerX, centerY, 0, scale, rot),
        //        renderX + rotateX(centerX, centerY, scale * 2, scale, rot),
        //         renderY +rotateY(centerX, centerY, scale * 2, scale, rot),
        //        renderX + rotateX(centerX, centerY, scale * 2, 0, rot),
        //         renderY +rotateY(centerX, centerY, scale * 2, 0, rot),
        //        0, 0, t.getRenderWidth(), t.getRenderHeight(), light, -1);
        //g.translate(-(centerX + (((t.getRenderWidth() - t.getRenderHeight())/2f)* (float)Math.sin(rot) - t.getRenderWidth()/2)), -(centerY - (((t.getRenderWidth() - t.getRenderHeight())/2f)* (float)Math.cos(rot) - t.getRenderHeight()/2)));
        //g.translate(-centerX, -centerY);
        //g.rotate(-(360 - state.get(ItemCannonTile.rotation) * 5));
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, ItemCannonTile tile, ItemInstance instance, float x, float y, float scale, int filter) {
        manager.getTexture(texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
    }
}
