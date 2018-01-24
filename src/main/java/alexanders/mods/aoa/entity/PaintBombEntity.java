package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.init.Tiles;
import alexanders.mods.aoa.net.PaintBombParticlePacket;
import alexanders.mods.aoa.render.BombRenderer;
import alexanders.mods.aoa.tile.ColourableTile;
import alexanders.mods.aoa.tile.entity.AssimilatedTileEntity;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.resourcePaintBomb;

public class PaintBombEntity extends BombEntity {
    private Colours colour = Colours.WHITE;

    public PaintBombEntity(IWorld world) {
        super(world);
        this.renderer = new BombRenderer(resourcePaintBomb);
    }

    public PaintBombEntity(IWorld world, Colours colour) {
        this(world);
        this.colour = colour;
    }

    @Override
    public void save(DataSet set) {
        super.save(set);
        set.addInt("colour", colour.ordinal());
    }

    @Override
    public void load(DataSet set) {
        super.load(set);
        colour = Colours.get(set.getInt("colour"));
    }

    @Override
    protected void damageAndDestroy(int x, int y) {
        paint(TileLayer.BACKGROUND, x, y);
        paint(TileLayer.MAIN, x, y);
    }

    private void paint(TileLayer layer, int x, int y) {
        TileState former = world.getState(layer, x, y);
        TileState newState = Tiles.assimilatedTile.getDefState().prop(ColourableTile.COLOUR, colour);
        if (former.getTile() == Tiles.assimilatedTile) {
            world.setState(layer, x, y, newState);
        } else if (former.getTile().isFullTile()) {
            BoundBox bb = former.getTile().getBoundBox(world, x, y, layer);
            world.setState(layer, x, y, newState);
            AssimilatedTileEntity te = world.getTileEntity(layer, x, y, AssimilatedTileEntity.class);
            te.tileName = former.getTile().getName();
            te.override = true;
            te.bb = bb;
        }
        PaintBombParticlePacket packet = new PaintBombParticlePacket(x, y, colour);
        if (RockBottomAPI.getNet().isServer()) {
            if (RockBottomAPI.getGame().isDedicatedServer()) {
                RockBottomAPI.getNet().sendToAllPlayersWithLoadedPos(world, packet, x, y);
            } else {
                RockBottomAPI.getNet().sendToAllPlayersWithLoadedPosExcept(world, packet, x, y, RockBottomAPI.getGame().getPlayer());
                packet.handle(RockBottomAPI.getGame(), null);
            }
        } else {
            packet.handle(RockBottomAPI.getGame(), null);
        }
    }
}
