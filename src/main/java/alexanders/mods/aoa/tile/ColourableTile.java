package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.item.ColourableItemTile;
import alexanders.mods.aoa.render.ColourableTileRenderer;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class ColourableTile extends TileBasic {
    public static final EnumProp<Colours> COLOUR = new EnumProp<>("colour", Colours.WHITE, Colours.class);

    public boolean fullTile = true;
    public boolean obscuresBackground = false;

    public ColourableTile(ResourceName name) {
        super(name);
        addProps(COLOUR);
        this.setForceDrop();
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new ColourableTileRenderer(name);
    }

    @Override
    protected ItemTile createItemTile() {
        return new ColourableItemTile(name);
    }

    @Override
    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        return getDefState().prop(COLOUR, Colours.get(instance.getMeta()));
    }

    @Override
    public boolean obscuresBackground(IWorld world, int x, int y, TileLayer layer) {
        return obscuresBackground;
    }

    @Override
    public boolean isFullTile() {
        return fullTile;
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        Item item = this.getItem();
        if (item != null) {
            return Collections.singletonList(new ItemInstance(item, 1, world.getState(layer, x, y).get(COLOUR).ordinal()));
        } else {
            return Collections.emptyList();
        }
    }
}
