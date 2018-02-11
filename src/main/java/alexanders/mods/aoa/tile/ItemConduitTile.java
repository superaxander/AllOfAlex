package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.ConduitLayer;
import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.item.ItemConduitItemTile;
import alexanders.mods.aoa.net.RemoveFilterPacket;
import alexanders.mods.aoa.render.ItemConduitTileRender;
import alexanders.mods.aoa.tile.entity.ItemConduitTileEntity;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class ItemConduitTile extends ColourableTile {
    public static EnumProp<ConduitConnections> CONNECTIONS = new EnumProp<>("connections", ConduitConnections.NONE, ConduitConnections.class);

    public ItemConduitTile(IResourceName name) {
        super(name);
        addProps(CONNECTIONS);
    }

    @Override
    public boolean canPlaceInLayer(TileLayer layer) {
        return layer == ConduitLayer.instance;
    }

    @Override
    protected ItemTile createItemTile() {
        return new ItemConduitItemTile(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        if (!getNet().isServer()) {
            if (Keys.KEY_REMOVE_FILTER.isDown()) {
                RemoveFilterPacket packet = new RemoveFilterPacket(x, y);
                if (getNet().isActive()) {
                    getNet().sendToServer(packet);
                } else {
                    packet.handle(RockBottomAPI.getGame(), null);
                }
            }
        }
        ItemConduitTileEntity te = world.getTileEntity(layer, x, y, ItemConduitTileEntity.class);
        te.mode = (te.mode + 1) % 4;
        return true;
    }

    @Override
    public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        super.onChangeAround(world, x, y, layer, changedX, changedY, changedLayer);
        if (changedLayer == ConduitLayer.instance) {
            TileState myState = world.getState(layer, x, y);
            ConduitConnections myConnections = myState.get(CONNECTIONS);
            TileState changedTile = world.getState(changedLayer, changedX, changedY);
            ConduitConnections newConnections;
            if (changedTile.getTile() == this && (changedTile.get(COLOUR) == Colours.WHITE || myState.get(COLOUR) == Colours.WHITE || changedTile.get(COLOUR) == myState.get(COLOUR))) { // If this is another conduit
                newConnections = myConnections.addConnection(changedX - x, changedY - y);
            } else {
                newConnections = myConnections.removeConnection(changedX - x, changedY - y);

            }
            if (myConnections != newConnections)
                world.setState(layer, x, y, myState.prop(CONNECTIONS, newConnections));
        }
    }

    @Override
    public boolean isFullTile() {
        return false;
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name) {
        return new ItemConduitTileRender(name);
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new ItemConduitTileEntity(world, x, y, layer);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }
}
