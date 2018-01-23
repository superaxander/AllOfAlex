package alexanders.mods.aoa.commands;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class SpawnCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: spawn [override] <x> <y> <entity> [params]");
    private static final ChatComponentText NOT_FOUND = new ChatComponentText(FormattingCode.RED + "The specified entity wasn't found");
    private static final ChatComponentText SOMETHING_WENT_WRONG = new ChatComponentText(FormattingCode.RED + "Something went wrong, either your entity parameters didn't fit the entity or the entity is invalid.");
    private static final ChatComponentText OVERRIDE_REQUIRED = new ChatComponentText(FormattingCode.RED + "Only the default constructor is available(or your parameters weren't accepted) please set override to true to use that constructor");

    public SpawnCommand() {
        super(createRes("spawn"), "Spawns an entity. Params: [override] <x> <y> <entity> [params]", 8);
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
        if (argNumber == 2) {
            List<String> entities = new ArrayList<>();
            for (IResourceName resourceName : RockBottomAPI.ENTITY_REGISTRY.getUnmodifiable().keySet()) {
                entities.add(resourceName.toString());
            }
            return entities;
        }
        return super.getAutocompleteSuggestions(args, argNumber, sender, game, chat);
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {
        if (args.length < 1)
            return USAGE;
        int offset = 0;
        if (args[0].equals("true"))
            offset = 1;
        IWorld world = game.getWorld();
        if (args.length < 3 + offset)
            return USAGE;
        try {
            double x = Double.parseDouble(args[offset]);
            double y = Double.parseDouble(args[offset + 1]);
            Class<? extends Entity> clazz = RockBottomAPI.ENTITY_REGISTRY.get(RockBottomAPI.createRes(args[offset + 2]));
            if (clazz == null)
                return NOT_FOUND;
            Entity e = null;
            Constructor defaultConstructor = null;
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> c : constructors) {
                Class<?>[] parameterTypes = c.getParameterTypes();
                if (c.isVarArgs())
                    continue;
                if (parameterTypes.length == 1) {
                    defaultConstructor = c;
                    if (args.length <= 3 + offset)
                        break;
                } else {
                    boolean matches = true;
                    Object[] params = new Object[parameterTypes.length];
                    for (int i = 0, counter = 0; i < parameterTypes.length; i++, counter++) {
                        if (parameterTypes[i] == IWorld.class) {
                            params[i] = world;
                            counter--;
                        } else {
                            if (counter + 1 + offset >= args.length - 2) {
                                matches = false;
                                break;
                            } else {
                                Map<Class<?>, Object> possible = toPossibleTypes(args[3 + counter + offset]);
                                if (possible.containsKey(parameterTypes[i])) {
                                    params[i] = possible.get(parameterTypes[i]);
                                } else {
                                    matches = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (matches)
                        e = (Entity) c.newInstance(params);

                }
            }
            if (e == null)
                if (defaultConstructor == null)
                    return SOMETHING_WENT_WRONG;
                else if (offset == 1)
                    e = (Entity) defaultConstructor.newInstance(world);
                else
                    return OVERRIDE_REQUIRED;
            e.x = x;
            e.y = y;
            world.addEntity(e);
        } catch (ClassCastException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return SOMETHING_WENT_WRONG;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return USAGE;
        }
        return null;
    }

    private Map<Class<?>, Object> toPossibleTypes(String arg) {
        // TODO: Make this more sophisticated, but for now try everything out
        HashMap<Class<?>, Object> map = new HashMap<>();
        map.put(String.class, arg);
        try {
            map.put(UUID.class, UUID.fromString(arg));
        } catch (IllegalArgumentException ignored) {
        }
        try {
            IResourceName name = RockBottomAPI.createRes(arg);
            map.put(IResourceName.class, name);
            Tile tile = RockBottomAPI.TILE_REGISTRY.get(name);
            if (tile != null) {
                map.put(Tile.class, tile);
                try {
                    ItemInstance instance = new ItemInstance(tile);
                    map.put(ItemInstance.class, instance);
                } catch (NullPointerException ignored) {
                }
            }
            Item item = RockBottomAPI.ITEM_REGISTRY.get(name);
            if (item != null) {
                map.put(Item.class, item);
                try {
                    ItemInstance instance = new ItemInstance(item);
                    map.put(ItemInstance.class, instance);
                } catch (NullPointerException ignored) {
                }
            }
        } catch (IllegalArgumentException ignored) {
        }
        if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
            map.put(Boolean.class, Boolean.parseBoolean(arg));
            map.put(boolean.class, Boolean.parseBoolean(arg));
        }
        try {
            map.put(Long.class, Long.parseLong(arg));
            map.put(long.class, Long.parseLong(arg));
            map.put(Integer.class, Integer.parseInt(arg));
            map.put(int.class, Integer.parseInt(arg));
            map.put(Short.class, Short.parseShort(arg));
            map.put(short.class, Short.parseShort(arg));
            map.put(Byte.class, Byte.parseByte(arg));
            map.put(byte.class, Byte.parseByte(arg));
        } catch (NumberFormatException ignored) {
        }
        try {
            map.put(Double.class, Double.parseDouble(arg));
            map.put(double.class, Double.parseDouble(arg));
            map.put(Float.class, Float.parseFloat(arg));
            map.put(float.class, Float.parseFloat(arg));
        } catch (NumberFormatException ignored) {
        }
        if (arg.length() == 1) {
            map.put(Character.class, arg.charAt(0));
            map.put(char.class, arg.charAt(0));
        }
        return map;
    }
}
