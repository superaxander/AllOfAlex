package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.render.CannonGui;
import alexanders.mods.aoa.tile.entity.ItemCannonContainer;
import alexanders.mods.aoa.tile.entity.ItemCannonTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemCannonTile extends CannonTile {
    public ItemCannonTile(ResourceName name) {
        super(name);
    }

    @Override
    public void onRightClick(IWorld world, int x, int y, TileLayer layer, boolean doRotate, AbstractEntityPlayer player) {
        if (doRotate) {
            world.setState(layer, x, y, world.getState(layer, x, y).cycleProp(rotation));
        } else {
            ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
            player.openGuiContainer(new CannonGui(player, te), new ItemCannonContainer(player, te));
        }
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new ItemCannonTileEntity(world, x, y, layer);
    }
}
