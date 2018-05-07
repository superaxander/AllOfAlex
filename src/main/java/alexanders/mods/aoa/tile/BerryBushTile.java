package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.item.ShearsItem;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.state.BoolProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.ITEM_REGISTRY;

public class BerryBushTile extends VariantTile {
    public static BoolProp empty = new BoolProp("empty", false);

    public BerryBushTile(ResourceName name) {
        super(name);
        this.addProps(empty);
    }

    @Override
    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        return super.getPlacementState(world, x, y, layer, instance, placer).prop(empty, true);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        if (RockBottomAPI.getNet().isServer() || !RockBottomAPI.getNet().isActive()) {
            ItemInstance selected = player.getInv().get(player.getSelectedSlot());
            if (selected != null && selected.getItem() instanceof ShearsItem) return false;
            TileState state = world.getState(layer, x, y);
            if (!state.get(empty)) {
                player.getInv().addExistingFirst(new ItemInstance(ITEM_REGISTRY.get(name.addSuffix(".berry")), 1), false);
                world.setState(x, y, state.prop(empty, true));
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void doBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer breaker, boolean isRightTool, boolean allowDrop) {
        super.doBreak(world, x, y, layer, breaker, isRightTool, allowDrop);
    }

    @Override
    public void updateRandomly(IWorld world, int x, int y, TileLayer layer) {
        if (Util.RANDOM.nextInt(16) == 0) {
            world.setState(layer, x, y, world.getState(x, y).prop(empty, false));
        }
    }
}
