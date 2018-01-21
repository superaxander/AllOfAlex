package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.ConduitLayer;
import alexanders.mods.aoa.tile.ConduitConnections;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static alexanders.mods.aoa.tile.ItemConduitTile.CONNECTIONS;

public class ItemConduitTileEntity extends TileEntity {
    public int mode = 0; // 0 = connection, 1=input, 2=output, 3=passive
    public int cooldown = 0;
    private int lastMode = 0;

    public ItemConduitTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (cooldown > 0) {
            cooldown--;
        } else {
            IInventoryHolder holder = getInventoryHolder();
            if (holder != null) {
                List<ItemConduitTileEntity> network = getNetwork(new ArrayList<>());
                switch (mode) {
                    case 1:
                        if (takeHerePutThere(network.stream().filter((it) -> it.mode == 2 || it.mode == 3).collect(Collectors.toList()), holder))
                            cooldown = 20;
                        break;
                    case 2:
                        if (takeTherePutHere(network.stream().filter((it) -> it.mode == 1 || it.mode == 3).collect(Collectors.toList()), holder))
                            cooldown = 20;
                        break;
                    default: //TODO: filters
                        break;
                }
            }
        }
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        set.addInt("mode", mode);
        set.addInt("cooldown", cooldown);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        mode = set.getInt("mode");
        cooldown = set.getInt("cooldown");
    }

    @Override
    protected boolean needsSync() {
        if (mode != lastMode) {
            lastMode = mode;
            return true;
        }
        return cooldown > 0;
    }

    private boolean takeHerePutThere(List<ItemConduitTileEntity> eligable, IInventoryHolder holder) {
        List<Integer> outputSlots = holder.getOutputSlots(Direction.NONE); //TODO: Support directional inventories
        for (Integer i : outputSlots) {
            ItemInstance item;
            if ((item = holder.getInventory().get(i)) != null) {
                for (ItemConduitTileEntity it : eligable) {
                    IInventoryHolder otherHolder = it.getInventoryHolder();
                    if (otherHolder != null) {
                        List<Integer> inputSlots = otherHolder.getInputSlots(item, Direction.NONE);
                        for (Integer i2 : inputSlots) {
                            ItemInstance otherItem;
                            if ((otherItem = otherHolder.getInventory().get(i2)) != null) {
                                if (otherItem.isEffectivelyEqual(item)) {
                                    holder.getInventory().remove(i, 1);
                                    otherHolder.getInventory().addToSlot(i2, item.copy().setAmount(1), false);
                                    return true;
                                }
                            } else {
                                holder.getInventory().remove(i, 1);
                                otherHolder.getInventory().addToSlot(i2, item.copy().setAmount(1), false);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean takeTherePutHere(List<ItemConduitTileEntity> eligable, IInventoryHolder holder) {
        List<Integer> inputSlots = holder.getOutputSlots(Direction.NONE); //TODO: Support directional inventories
        for (int i : inputSlots) {
            ItemInstance item = holder.getInventory().get(i);
            for (ItemConduitTileEntity it : eligable) {
                IInventoryHolder otherHolder = it.getInventoryHolder();
                if (otherHolder != null) {
                    List<Integer> outputSlots = otherHolder.getOutputSlots(Direction.NONE);
                    for (Integer i2 : outputSlots) {
                        ItemInstance otherItem;
                            if ((otherItem = otherHolder.getInventory().get(i2)) != null && (otherItem.isEffectivelyEqual(item) || item == null)) {
                                otherHolder.getInventory().remove(i2, 1);
                                holder.getInventory().addToSlot(i, otherItem.copy().setAmount(1), false);
                                return true;
                            }
                    }
                }
            }
        }
        return false;
    }

    private IInventoryHolder getInventoryHolder() {
        TileEntity te = world.getTileEntity(TileLayer.MAIN, x, y);
        if (te instanceof IInventoryHolder) {
            return (IInventoryHolder) te;
        }
        return null;
    }

    private List<ItemConduitTileEntity> getNetwork(List<Pos2> exclusions) {
        ConduitConnections conduitConnections = world.getState(layer, x, y).get(CONNECTIONS);
        ArrayList<ItemConduitTileEntity> network = new ArrayList<>();
        exclusions.add(new Pos2(x, y));
        switch (conduitConnections) {
            case NONE:
                break;
            case TOP:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {
                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_BOTTOM:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_RIGHT:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_LEFT:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_RIGHT_BOTTOM:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_RIGHT_LEFT:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_BOTTOM_LEFT:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case TOP_RIGHT_BOTTOM_LEFT:
                if (!exclusions.contains(new Pos2(x, y + 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y + 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case RIGHT:
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case RIGHT_BOTTOM:
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case RIGHT_LEFT:
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case RIGHT_BOTTOM_LEFT:
                if (!exclusions.contains(new Pos2(x + 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x + 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case BOTTOM:
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case BOTTOM_LEFT:
                if (!exclusions.contains(new Pos2(x, y - 1))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x, y - 1, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
            case LEFT:
                if (!exclusions.contains(new Pos2(x - 1, y))) {
                    ItemConduitTileEntity te = world.getTileEntity(ConduitLayer.instance, x - 1, y, ItemConduitTileEntity.class);
                    if (te != null) {

                        network.addAll(te.getNetwork(exclusions));
                        network.add(te);
                    }
                }
                break;
        }
        return network;
    }
}
