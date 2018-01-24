package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.item.AssimilatedItemTile;
import alexanders.mods.aoa.render.AssimilatedTileRenderer;
import alexanders.mods.aoa.tile.entity.AssimilatedTileEntity;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public class AssimilatedTile extends ColourableTile {
    private static final ChatComponentText displayMessage = new ChatComponentText("This is a temporary way to cycle between colors! It will use crafting in the future!");

    public AssimilatedTile(IResourceName name) {
        super(name);
    }

    // @Override
    //public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
    //if (RockBottomAPI.getGame().getChatLog().getMessages().isEmpty() || RockBottomAPI.getGame().getChatLog().getMessages().get(0) != displayMessage)
    //    player.sendMessageTo(RockBottomAPI.getGame().getChatLog(), displayMessage);
    //world.setState(layer, x, y, world.getState(layer, x, y).cycleProp(COLOUR));
    //return true;
    //}


    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer) {
        AssimilatedTileEntity te = world.getTileEntity(layer, x, y, AssimilatedTileEntity.class);
        if (te.override)
            return te.bb;
        try {
            return RockBottomAPI.TILE_REGISTRY.get(te.tileName).getBoundBox(world, x, y, layer);
        } catch (Throwable ignored) {
        }
        return super.getBoundBox(world, x, y, layer);
    }

    @Override
    protected ItemTile createItemTile() {
        return new AssimilatedItemTile(name);
    }

    @Override
    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        super.doPlace(world, x, y, layer, instance, placer);
        if (instance.hasAdditionalData() && instance.getAdditionalData().hasKey("tileName")) {
            AssimilatedTileEntity te = (AssimilatedTileEntity) world.getTileEntity(layer, x, y);
            te.tileName = RockBottomAPI.createRes(instance.getAdditionalData().getString("tileName"));
        }
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        List<ItemInstance> drops = super.getDrops(world, x, y, layer, destroyer);
        AssimilatedTileEntity te = (AssimilatedTileEntity) world.getTileEntity(layer, x, y);
        if (te.tileName != null)
            drops.forEach(itemInstance -> itemInstance.getOrCreateAdditionalData().addString("tileName", te.tileName.toString()));
        return drops;
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new AssimilatedTileEntity(world, x, y, layer);
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name) {
        return new AssimilatedTileRenderer(name);
    }
}
