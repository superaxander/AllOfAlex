package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.net.CannonRightClickPacket;
import alexanders.mods.aoa.render.CannonRenderer;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getGame;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public abstract class CannonTile extends TileBasic {
    public static final IntProp rotation = new IntProp("rotation", 0, 36);

    public CannonTile(ResourceName name) {
        super(name);
        addProps(rotation);
    }

    public abstract void onRightClick(IWorld world, int x, int y, TileLayer layer, boolean doRotate, AbstractEntityPlayer player);

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new CannonRenderer(name);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        if (getNet().isActive()) {
            IPacket packet = new CannonRightClickPacket(player.getUniqueId(), Keys.KEY_ROTATE.isDown(), x, y, layer, this instanceof BombCannonTile);
            if (getNet().isClient()) getNet().sendToServer(packet);
            else packet.handle(getGame(), null);
        } else {
            onRightClick(world, x, y, layer, Keys.KEY_ROTATE.isDown(), player);
        }
        return true;
    }
}
