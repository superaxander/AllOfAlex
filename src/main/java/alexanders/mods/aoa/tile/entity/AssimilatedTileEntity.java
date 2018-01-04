package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class AssimilatedTileEntity extends TileEntity {
    public IResourceName tileName;

    public AssimilatedTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        if (tileName != null)
            set.addString("tileName", tileName.toString());
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        if (set.hasKey("tileName"))
            tileName = RockBottomAPI.createRes(set.getString("tileName"));
    }
}
