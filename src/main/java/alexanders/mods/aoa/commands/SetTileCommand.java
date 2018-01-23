package alexanders.mods.aoa.commands;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class SetTileCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: settile <x> <y> <layer> <tile> [state]");
    private static final ChatComponentText CANT_BE_PLACED = new ChatComponentText(FormattingCode.RED + "This tile can't be placed in the specified layer");

    public SetTileCommand() {
        super(createRes("settile"), "Sets the state of a tile. Params: <x> <y> <layer> <tile> [state]", 5);
    }

    @Override
    public List<String> getAutocompleteSuggestions(String[] args, int argNumber, ICommandSender sender, IGameInstance game, IChatLog chat) {
        if (sender instanceof AbstractEntityPlayer) {
            AbstractEntityPlayer player = (AbstractEntityPlayer) sender;
            switch (argNumber) {
                case 0:
                    return Collections.singletonList(String.valueOf(Util.floor(player.x)));
                case 1:
                    return Collections.singletonList(String.valueOf(Util.floor(player.y)));
            }
        }
        switch (argNumber) {
            case 2:
                List<String> layers = new ArrayList<>();
                for (TileLayer tileLayer : TileLayer.getAllLayers()) {
                    layers.add(tileLayer.getName().toString());
                }
                return layers;
            case 3:
                List<String> tiles = new ArrayList<>();
                for (IResourceName resourceName : RockBottomAPI.TILE_REGISTRY.getUnmodifiable().keySet()) {
                    tiles.add(resourceName.toString());
                }
                return tiles;
            //TODO: Add state auto completions
        }
        return super.getAutocompleteSuggestions(args, argNumber, sender, game, chat);
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {
        if (args.length < 4)
            return USAGE;
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            TileLayer layer = getLayer(args[2]);
            Tile tile = RockBottomAPI.TILE_REGISTRY.get(RockBottomAPI.createRes(args[3]));
            if (!tile.canPlaceInLayer(layer))
                return CANT_BE_PLACED;
            if (args.length > 4) {
                TileState state = RockBottomAPI.TILE_STATE_REGISTRY.get(RockBottomAPI.createRes(args[3] + ";" + args[4]));
                RockBottomAPI.getGame().getWorld().setState(layer, x, y, state);
            } else {
                RockBottomAPI.getGame().getWorld().setState(layer, x, y, tile.getDefState());
            }
        } catch (IllegalArgumentException e) {
            return USAGE;
        }
        return null;
    }

    private TileLayer getLayer(String layerName) {
        String domain = null;
        String[] split = layerName.split("/");
        if (split.length > 1) {
            domain = split[0];
            layerName = String.join("", Arrays.copyOfRange(split, 1, split.length));
        }
        List<TileLayer> layers = TileLayer.getAllLayers();
        for (TileLayer layer : layers) {
            if (domain != null && !layer.getName().getDomain().equalsIgnoreCase(domain))
                continue;
            if (layer.getName().getResourceName().equalsIgnoreCase(layerName))
                return layer;
        }
        throw new IllegalArgumentException();
    }
}
