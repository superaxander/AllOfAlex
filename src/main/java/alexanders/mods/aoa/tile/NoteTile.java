package alexanders.mods.aoa.tile;

import alexanders.mods.aoa.Util;
import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.net.NotePlayPacket;
import alexanders.mods.aoa.tile.entity.NoteTileEntity;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.ISound;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class NoteTile extends TileBasic { // Add a jukebox too that can stream audio from files and the internet
    public NoteTile(IResourceName name) {
        super(name);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new NoteTileEntity(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        NoteTileEntity te = world.getTileEntity(layer, x, y, NoteTileEntity.class);
        if ((te.lastUse + 200) < System.currentTimeMillis()) {
            te.lastUse = System.currentTimeMillis();
            if (getNet().isServer()) {
                getNet().sendToAllPlayersWithLoadedPosExcept(world, new NotePlayPacket(te.type, te.note, x, y, layer.index()), x, y, player);
            } else {
                if (Keys.KEY_CHANGE_NOTE.isDown()) {
                    te.note = Util.cycleEnum(te.note);
                } else if (Keys.KEY_CHANGE_INSTRUMENT.isDown()) {
                    te.type = Util.cycleEnum(te.type);
                }
                ISound sound = RockBottomAPI.getGame().getAssetManager().getSound(te.type.name.addSuffix(te.note.name));
                sound.playAt(x + .5, y + .5, layer.index());
            }
        }
        return true;
    }
}
