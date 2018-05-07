package alexanders.mods.aoa.item;

import alexanders.mods.aoa.render.DamageableRenderer;
import alexanders.mods.aoa.tile.VariantTile;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Tiles.brightLeaves;

public class ShearsItem extends ItemBasic implements IDamageable {

    public ShearsItem(ResourceName name) {
        super(name);
        this.maxAmount = 1;
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name) {
        return new DamageableRenderer<ShearsItem>(name);
    }

    @Override
    public int getHighestPossibleMeta() {
        return 128;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        if (RockBottomAPI.getNet().isServer() || !RockBottomAPI.getNet().isActive()) {
            TileState state = world.getState(layer, x, y);
            Tile t = state.getTile();
            if (t instanceof VariantTile || t == GameContent.TILE_GRASS_TUFT || t == GameContent.TILE_FLOWER || t == GameContent.TILE_LEAVES || t == brightLeaves) {
                t.doBreak(world, x, y, layer, player, true, true);
                instance.setMeta(instance.getMeta() + 1);
                if (instance.getMeta() >= 128) {
                    IInventory inv = player.getInv();
                    inv.set(inv.getItemIndex(instance), null);
                }
                return true;
            }
        }
        return layer == TileLayer.MAIN;
    }
}
