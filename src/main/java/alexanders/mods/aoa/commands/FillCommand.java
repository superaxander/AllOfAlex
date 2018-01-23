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

public class FillCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: fill <x1> <y1> <x2> <y2> <layer> <tile> [state]");
    private static final ChatComponentText CANT_BE_PLACED = new ChatComponentText(FormattingCode.RED + "This tile can't be placed in the specified layer");

    public FillCommand() {
        super(createRes("fill"), "Fills an area with a specific tile. Params: <x1> <y1> <x2> <y2> <layer> <tile> [state]", 6);
    }

    @Override
    public List<String> getAutocompleteSuggestions(String[] args, int argNumber, ICommandSender sender, IGameInstance game, IChatLog chat) {
        if (sender instanceof AbstractEntityPlayer) {
            AbstractEntityPlayer player = (AbstractEntityPlayer) sender;
            switch (argNumber) {
                case 0:
                case 2:
                    return Collections.singletonList(String.valueOf(Util.floor(player.x)));
                case 1:
                case 3:
                    return Collections.singletonList(String.valueOf(Util.floor(player.y)));
            }
        }
        switch (argNumber) {
            case 4:
                List<String> layers = new ArrayList<>();
                for (TileLayer tileLayer : TileLayer.getAllLayers()) {
                    layers.add(tileLayer.getName().toString());
                }
                return layers;
            case 5:
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
        if (args.length < 6)
            return USAGE;
        try {
            int x1 = Integer.parseInt(args[0]);
            int y1 = Integer.parseInt(args[1]);
            int x2 = Integer.parseInt(args[2]);
            int y2 = Integer.parseInt(args[3]);
            TileLayer layer = getLayer(args[4]);
            Tile tile = RockBottomAPI.TILE_REGISTRY.get(RockBottomAPI.createRes(args[5]));
            if (!tile.canPlaceInLayer(layer))
                return CANT_BE_PLACED;
            TileState state = null;
            if (args.length > 6) {
                state = RockBottomAPI.TILE_STATE_REGISTRY.get(RockBottomAPI.createRes(args[5] + ";" + args[6]));
            }
            if (state == null) {
                state = tile.getDefState();
            }
            int startX = Math.min(x1, x2);
            int endX = Math.max(x1, x2);
            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            for (int x = startX; x <= endX; x++)
                for (int y = startY; y <= endY; y++)
                    RockBottomAPI.getGame().getWorld().setState(layer, x, y, state);
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
