package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.entity.BombEntity;
import alexanders.mods.aoa.entity.MiningBombEntity;
import alexanders.mods.aoa.entity.PaintBombEntity;
import alexanders.mods.aoa.init.Items;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BombCannonTileEntity extends CannonTileEntity {
    public BombCannonTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        maxCooldown = 400;
    }

    @Override
    protected Entity createEntity(IWorld world, ItemInstance itemInstance, double x, double y, double motionX, double motionY) {
        return itemInstance.getItem() == Items.bombItem ? new BombEntity(world) :
                itemInstance.getItem() == Items.paintBombItem ? new PaintBombEntity(world, Colours.get(itemInstance.getMeta())) : new MiningBombEntity(world);
    }
}
