package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.render.CannonGui;
import alexanders.mods.aoa.tile.entity.BombCannonContainer;
import alexanders.mods.aoa.tile.entity.BombCannonTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BombCannonTile extends CannonTile {

    public BombCannonTile(IResourceName name) {
        super(name);
    }

    @Override
    public void onRightClick(IWorld world, int x, int y, TileLayer layer, boolean doRotate, AbstractEntityPlayer player) {
        if (doRotate) {
            world.setState(layer, x, y, world.getState(layer, x, y).cycleProp(rotation));
        } else {
            BombCannonTileEntity te = world.getTileEntity(x, y, BombCannonTileEntity.class);
            player.openGuiContainer(new CannonGui(player, te), new BombCannonContainer(player, te));
        }
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new BombCannonTileEntity(world, x, y, layer);
    }
}
