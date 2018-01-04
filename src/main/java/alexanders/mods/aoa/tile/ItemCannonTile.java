package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.net.CannonRightClickPacket;
import alexanders.mods.aoa.render.ItemCannonGui;
import alexanders.mods.aoa.render.ItemCannonRenderer;
import alexanders.mods.aoa.tile.entity.ItemCannonContainer;
import alexanders.mods.aoa.tile.entity.ItemCannonTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class ItemCannonTile extends TileBasic {
    public static final IntProp rotation = new IntProp("rotation", 0, 36);

    public ItemCannonTile(IResourceName name) {
        super(name);
        addProps(rotation);
    }

    public static void onRightClick(IWorld world, int x, int y, TileLayer layer, boolean doRotate, AbstractEntityPlayer player) {
        if (doRotate) {
            world.setState(layer, x, y, world.getState(layer, x, y).cycleProp(ItemCannonTile.rotation));
        } else {
            ItemCannonTileEntity te = (ItemCannonTileEntity) world.getTileEntity(x, y);
            player.openGuiContainer(new ItemCannonGui(player, te), new ItemCannonContainer(player, te));
        }
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name) {
        return new ItemCannonRenderer(name);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new ItemCannonTileEntity(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        if (getNet().isActive()) {
            if (getNet().isClient())
                getNet().sendToServer(new CannonRightClickPacket(player.getUniqueId(), Keys.KEY_ROTATE.isDown(), x, y, layer));
        } else {
            onRightClick(world, x, y, layer, Keys.KEY_ROTATE.isDown(), player);
        }
        return true;
    }
}
