package alexanders.mods.aoa.commands;

import alexanders.mods.aoa.net.ExportMenuPacket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Util;

import java.util.Collections;
import java.util.List;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class ExportMenuCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: export_menu <x1> <y1> <x2> <y2> <filename>");
    private static final ChatComponentText INVALID_SIZE = new ChatComponentText(FormattingCode.RED + "Only a 16x16 area is supported");
    private static final ChatComponentText INVALID_FILENAME = new ChatComponentText(FormattingCode.RED + "The filename must only contain alphanumerical characters");

    public ExportMenuCommand() {
        super(createRes("export_menu"), "Writes a menu theme to a file. Params: <x1> <y1> <x2> <y2> <filename>", 0);
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
        return super.getAutocompleteSuggestions(args, argNumber, sender, game, chat);
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {
        AbstractEntityPlayer player = RockBottomAPI.getGame().getWorld().getPlayer(sender.getUniqueId());
        if (player == null) return new ChatComponentText(FormattingCode.RED + "Only players can execute this command");
        if (args.length != 5) return USAGE;
        try {
            int x1 = Integer.parseInt(args[0]);
            int y1 = Integer.parseInt(args[1]);
            int x2 = Integer.parseInt(args[2]);
            int y2 = Integer.parseInt(args[3]);
            if (Math.abs(x1 - x2) != 16 || Math.abs(y1 - y2) != 16) return INVALID_SIZE;
            for (char c : args[4].toCharArray())
                if (!Character.isLetterOrDigit(c)) return INVALID_FILENAME;
            IPacket packet = new ExportMenuPacket(args[4], x1, y1, x2, y2);
            if (game.isDedicatedServer() || game.getPlayer() != sender) player.sendPacket(packet);
            else packet.handle(game, null);
        } catch (IllegalArgumentException e) {
            return USAGE;
        }
        return null;
    }
}
