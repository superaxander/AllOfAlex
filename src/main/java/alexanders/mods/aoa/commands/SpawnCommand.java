package alexanders.mods.aoa.commands;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
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
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static de.ellpeck.rockbottom.api.RockBottomAPI.*;

public class SpawnCommand extends Command {
    private static final ChatComponentText USAGE = new ChatComponentText(FormattingCode.RED + "Usage: spawn [override] <x> <y> <entity> [params]");
    private static final ChatComponentText NOT_FOUND = new ChatComponentText(FormattingCode.RED + "The specified entity wasn't found");
    private static final ChatComponentText SOMETHING_WENT_WRONG = new ChatComponentText(
            FormattingCode.RED + "Something went wrong, either your entity parameters didn't fit the entity or the entity is invalid.");
    private static final ChatComponentText OVERRIDE_REQUIRED = new ChatComponentText(
            FormattingCode.RED + "Only the default constructor is available(or your parameters weren't accepted) please set override to true to use that constructor");

    public SpawnCommand() {
        super(createRes("spawn"), "Spawns an entity. Params: [override] <x> <y> <entity> [params]", 8);
    }

    @Override
    public List<String> getAutocompleteSuggestions(String[] args, int argNumber, ICommandSender sender, IGameInstance game, IChatLog chat) {
        int offset = (args.length > 0 && args[0].equalsIgnoreCase("true")) ? 1 : 0;
        if (sender instanceof AbstractEntityPlayer) {
            AbstractEntityPlayer player = (AbstractEntityPlayer) sender;
            switch (argNumber) {
                case 0:
                    return Arrays.asList(String.valueOf(Util.floor(player.x)), "true");
                case 1:
                    if (offset == 1) return Collections.singletonList(String.valueOf(Util.floor(player.x)));
                    else return Collections.singletonList(String.valueOf(Util.floor(player.y)));
                case 2:
                    if (offset == 1) return Collections.singletonList(String.valueOf(Util.floor(player.y)));
                    break;
            }
        }
        if (argNumber == 2 + offset) {
            return registryToStrings(ENTITY_REGISTRY);
        } else if (argNumber > 2 + offset) {
            try {
                ArrayList<String> suggestions = new ArrayList<>();
                Constructor<?>[] constructors = getConstructors(args[2 + offset]);
                for (Constructor<?> constructor : constructors) {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes.length < 2) {
                        continue;
                    }
                    int parameteroffset = 0;
                    for (int i = 0; i < parameterTypes.length && i - parameteroffset < argNumber - 2 - offset; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        if (parameterType == IWorld.class) {
                            parameteroffset = 1;
                        } else if (i - parameteroffset == argNumber - 3 - offset) {
                            if ((parameterType == Integer.class || parameterType == int.class) && sender instanceof AbstractEntityPlayer) {
                                suggestions.add(String.valueOf(Util.floor(((AbstractEntityPlayer) sender).x)));
                                suggestions.add(String.valueOf(Util.floor(((AbstractEntityPlayer) sender).y)));
                            } else if ((parameterType == Double.class || parameterType == double.class) && sender instanceof AbstractEntityPlayer) {
                                suggestions.add(String.valueOf(((AbstractEntityPlayer) sender).x));
                                suggestions.add(String.valueOf(((AbstractEntityPlayer) sender).y));
                            } else if (parameterType == UUID.class) {
                                suggestions.addAll(chat.getPlayerSuggestions());
                            } else if (parameterType == ItemInstance.class) {
                                suggestions.addAll(registryToStrings(ITEM_REGISTRY));
                            } else if (parameterType == Tile.class) {
                                suggestions.addAll(registryToStrings(TILE_REGISTRY));
                            } else if (parameterType == TileState.class) {
                                suggestions.addAll(registryToStrings(TILE_STATE_REGISTRY));
                            } else if (parameterType.isArray()) {
                                suggestions.add("[]");
                            } else if (parameterType == DataSet.class) {
                                suggestions.add("{}");
                            } else if (parameterType.isEnum()) {
                                suggestions.addAll(Arrays.stream(parameterType.getEnumConstants()).map(Object::toString).collect(Collectors.toList()));
                            }
                        }
                    }
                }
                return suggestions;
            } catch (NoClassDefFoundError e) {
                return Collections.emptyList();
            }
        }
        return super.getAutocompleteSuggestions(args, argNumber, sender, game, chat);
    }

    private List<String> registryToStrings(NameRegistry<?> registry) {
        List<String> list = new ArrayList<>();
        for (ResourceName resourceName : registry.getUnmodifiable().keySet()) {
            String toString = resourceName.toString();
            list.add(toString);
        }
        return list;
    }

    private Constructor<?>[] getConstructors(String entityName) throws NoClassDefFoundError {
        Class<? extends Entity> clazz = ENTITY_REGISTRY.get(new ResourceName(entityName));
        if (clazz == null) throw new NoClassDefFoundError("Can't find class for that entity name");
        return clazz.getConstructors();
    }

    @Override
    public ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat) {
        if (args.length < 1) return USAGE;
        int offset = 0;
        if (args[0].equals("true")) offset = 1;
        IWorld world = game.getWorld();
        if (args.length < 3 + offset) return USAGE;
        try {
            double x = Double.parseDouble(args[offset]);
            double y = Double.parseDouble(args[offset + 1]);

            Constructor<?>[] constructors = getConstructors(args[offset + 2]);
            Entity e = null;
            Constructor defaultConstructor = null;

            for (Constructor<?> c : constructors) {
                Class<?>[] parameterTypes = c.getParameterTypes();
                if (parameterTypes.length == 1) {
                    defaultConstructor = c;
                    if (args.length <= 3 + offset) break;
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
                                Map<Class<?>, Object> possible = toPossibleTypes(parameterTypes[i], args[3 + counter + offset]);
                                if (possible.containsKey(parameterTypes[i])) {
                                    params[i] = possible.get(parameterTypes[i]);
                                } else {
                                    matches = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (matches) e = (Entity) c.newInstance(params);

                }
            }
            if (e == null) if (defaultConstructor == null) return SOMETHING_WENT_WRONG;
            else if (offset == 1) e = (Entity) defaultConstructor.newInstance(world);
            else return OVERRIDE_REQUIRED;
            e.x = x;
            e.y = y;
            world.addEntity(e);
        } catch (ClassCastException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return SOMETHING_WENT_WRONG;
        } catch (NoClassDefFoundError e) {
            return NOT_FOUND;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return USAGE;
        }
        return null;
    }

    private Map<Class<?>, Object> toPossibleTypes(Class<?> parameterType, String arg) {
        // TODO: Find some way to do strings with spaces?
        HashMap<Class<?>, Object> map = new HashMap<>();
        try {
            if (parameterType.isEnum()) {
                map.put(parameterType, parameterType.getMethod("valueOf", String.class).invoke(parameterType, arg));
                return map;
            }
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ignored) {
        }
        if (parameterType.isArray() || parameterType.isAssignableFrom(List.class)) {
            Class<?> componentType = parameterType.getComponentType();
            if (arg.startsWith("[") && arg.endsWith("]")) {
                String[] split = arg.substring(1, arg.length() - 1).split(",");
                ArrayList<Object> list = new ArrayList<>(split.length); //TODO: Support DataSet[]?
                boolean succeeded = true;
                for (String s : split) {
                    Map<Class<?>, Object> types = toPossibleTypes(componentType, s);
                    if (!types.containsKey(componentType)) {
                        succeeded = false;
                        break;
                    }
                    list.add(types.get(componentType));
                }
                if (succeeded) {
                    if (parameterType.isAssignableFrom(List.class)) {
                        map.put(List.class, list); // This might not work!
                    } else {
                        Object array = Array.newInstance(parameterType.getComponentType(), list.size());
                        for (int i = 0; i < list.size(); i++) {
                            Array.set(array, i, list.get(i));
                        }
                        map.put(parameterType, array);
                    }
                }
            }
        }
        try {
            if (parameterType == DataSet.class) {
                if (arg.startsWith("{") && arg.endsWith("}")) {
                    String[] split = arg.substring(1, arg.length() - 1).split(",");
                    Map<String, String> setMap = new HashMap<>(split.length);
                    boolean succeeded = true;
                    for (String s : split) {
                        String[] split2 = s.split("=", 2);
                        if (split2.length < 2) {
                            succeeded = false;
                            break;
                        }
                        setMap.put(split2[0], split2[1]);
                    }
                    if (succeeded) {
                        DataSet set = new DataSet();
                        Set<Class<? extends DataPart>> values = PART_REGISTRY.getUnmodifiable().values();
                        setMap.forEach((key, value) -> {
                            Map<Class<?>, Object> types = toPossibleTypes(String.class, value);
                            for (Class<? extends DataPart> clazz : values) {
                                TypeVariable<? extends Class<? extends DataPart>>[] typeParameters = clazz.getTypeParameters();
                                if (typeParameters.length == 1) {
                                    try {
                                        Class<?> typeClass = Class.forName(typeParameters[0].getName());
                                        if (types.containsKey(typeClass)) {
                                            set.addPart(clazz.getConstructor(String.class, typeClass).newInstance(key, types.get(typeClass)));
                                        }
                                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            }
                        });
                        map.put(DataSet.class, set);
                    }
                }
            }
        } catch (IllegalArgumentException ignored) {
        }
        map.put(String.class, arg);
        try {
            map.put(UUID.class, UUID.fromString(arg));
        } catch (IllegalArgumentException ignored) {
        }
        try {
            ResourceName name = new ResourceName(arg);
            map.put(ResourceName.class, name);
            Tile tile = TILE_REGISTRY.get(name);
            if (tile != null) {
                map.put(Tile.class, tile);
                try {
                    ItemInstance instance = new ItemInstance(tile);
                    map.put(ItemInstance.class, instance);
                } catch (NullPointerException ignored) {
                }
            }
            TileState state = TILE_STATE_REGISTRY.get(name);
            if (state != null) {
                map.put(TileState.class, state);
                try {
                    ItemInstance instance = new ItemInstance(state.getTile());
                    map.put(ItemInstance.class, instance);
                } catch (NullPointerException ignored) {
                }
            }
            Item item = ITEM_REGISTRY.get(name);
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
