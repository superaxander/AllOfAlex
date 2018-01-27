package alexanders.mods.aoa.tile.entity;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemCannonTileEntity extends CannonTileEntity {
    public ItemCannonTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    protected Entity createEntity(IWorld world, ItemInstance itemInstance) {
        return new EntityItem(world, itemInstance.setAmount(1));
    }
}
