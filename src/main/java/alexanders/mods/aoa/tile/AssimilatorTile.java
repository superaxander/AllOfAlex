package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.render.AssimilatorGui;
import alexanders.mods.aoa.tile.entity.AssimilatorContainer;
import alexanders.mods.aoa.tile.entity.AssimilatorTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.resourceAssimilator;

public class AssimilatorTile extends TileBasic {
    public AssimilatorTile() {
        super(resourceAssimilator);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileEntity te = world.getTileEntity(layer, x, y);
        if (te instanceof AssimilatorTileEntity) {
            player.openGuiContainer(new AssimilatorGui(player, (AssimilatorTileEntity) te), new AssimilatorContainer(player, ((AssimilatorTileEntity) te).inventory));
            return true;
        }
        return super.onInteractWith(world, x, y, layer, mouseX, mouseY, player);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new AssimilatorTileEntity(world, x, y, layer);
    }
}
