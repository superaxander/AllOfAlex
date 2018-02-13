package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.render.JukeboxGui;
import alexanders.mods.aoa.tile.entity.JukeboxTileEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class JukeboxTile extends TileBasic{
    public JukeboxTile(IResourceName name) {
        super(name);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new JukeboxTileEntity(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        player.openGui(new JukeboxGui(world.getTileEntity(layer, x, y, JukeboxTileEntity.class)));
        return true;
    }
}
