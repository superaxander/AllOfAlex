package alexanders.mods.aoa.item;

import alexanders.mods.aoa.entity.MagnetPulseEntity;
import alexanders.mods.aoa.render.CooldownableRenderer;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class MagnetItem extends ItemBasic implements ICooldownable {
    private static final ResourceName COOLDOWN = createRes("cooldown");
    
    public MagnetItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        ModBasedDataSet addData = instance.getOrCreateAdditionalData();
        if (addData.getInt(COOLDOWN) <= 0) {
            MagnetPulseEntity entity = new MagnetPulseEntity(world);
            entity.x = player.x;
            entity.y = player.y + .5;
            world.addEntity(entity);
            addData.addInt(COOLDOWN, 400);
            return true;
        } else return false;
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name) {
        return new CooldownableRenderer(name);
    }

    @Override
    public float getMaxCooldown() {
        return 400f;
    }
}
