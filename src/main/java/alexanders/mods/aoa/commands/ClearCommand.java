package alexanders.mods.aoa.commands;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class ClearCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: clear <player> [slot_id]");
    private static final ChatComponentText PLAYER_NOT_FOUND = new ChatComponentText(FormattingCode.RED + "The specified player was not found");
    private static final ChatComponentText INVALID_ID = new ChatComponentText(FormattingCode.RED + "The specified slot id was not found in the player's inventory");

    public ClearCommand() {
        super(createRes("clear"), "Clears a players inventory. Params: <player> [slot_id]", 8);
    }

    @Override
    public List<String> getAutocompleteSuggestions(String[] args, int argNumber, ICommandSender sender, IGameInstance game, IChatLog chat) {
        if(argNumber == 0) {
            //List<String> suggestions = game.getWorld().getAllPlayers().stream().map(player -> player.getUniqueId().toString()).collect(Collectors.toList());
            //suggestions.addAll(game.getWorld().getAllPlayers().stream().map(AbstractEntityPlayer::getName).collect(Collectors.toList()));
            return chat.getPlayerSuggestions();
        }
        return super.getAutocompleteSuggestions(args, argNumber, sender, game, chat);
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {
        if(args.length >= 1) {
            String name = args[0];
            UUID uuid = null;
            try {
                uuid = UUID.fromString(name);
            }catch (IllegalArgumentException ignored){
            }
            AbstractEntityPlayer player;
            if(uuid != null)
                player = game.getWorld().getPlayer(uuid);
            else
                player = game.getWorld().getPlayer(name);
            if(player == null)
                return PLAYER_NOT_FOUND;
            Inventory inventory = player.getInv();
            if(args.length >= 2) {
                try{
                    int id = Integer.parseInt(args[1]);
                    if(id < 0 || id >= inventory.getSlotAmount())
                        return INVALID_ID;
                    inventory.set(id, null);
                }catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return USAGE;
                }
            }else {
                int size = inventory.getSlotAmount();
                for (int i = 0; i < size; i++) {
                    inventory.set(i, null);
                }
            }
        }else {
            return USAGE;
        }
        return null;
    }
}
