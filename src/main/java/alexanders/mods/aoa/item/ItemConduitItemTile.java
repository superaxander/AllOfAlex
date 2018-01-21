package alexanders.mods.aoa.item;

import alexanders.mods.aoa.ConduitLayer;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Iterator;

public class ItemConduitItemTile extends ColourableItemTile {
    public ItemConduitItemTile(IResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) { // Edited version of the decompiled version of IApiHandler.placeTile
        Tile currentTile;
        TileState currentState;
        IResourceName soundName;
        Tile tile = getTile();
        if (layer == ConduitLayer.instance && (currentTile = world.getState(layer, x, y).getTile()) != tile && currentTile.canReplace(world, x, y, layer) && tile.canPlace(world, x, y, layer)) {
            if (!world.isClient()) {
                tile.doPlace(world, x, y, layer, instance, player);
                player.getInv().remove(player.getSelectedSlot(), 1);
                
                if ((currentState = world.getState(layer, x, y)).getTile() == tile && (soundName = tile.getPlaceSound(player.world, x, y, layer, player, currentState)) != null) {
                    world.playSound(soundName, (double)x + 0.5D, (double)y + 0.5D, (double)layer.index(), 1.0F, 1.0F);
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
}
