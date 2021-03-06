package alexanders.mods.aoa.tile.entity;

import alexanders.mods.aoa.ConduitLayer;
import alexanders.mods.aoa.tile.ConduitConnections;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
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
    public ItemFilter filter = null;
    private int lastMode = 0;
    private ItemFilter lastFilter = null;

    public ItemConduitTileEntity(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (cooldown > 0) {
            cooldown--;
        } else {
            IFilteredInventory inv = getConnectedInventory();
            if (inv != null) {
                List<ItemConduitTileEntity> network = getNetwork(new ArrayList<>());
                switch (mode) {
                    case 1:
                        if (takeHerePutThere(network.stream().filter((it) -> it.mode == 2 || it.mode == 3).collect(Collectors.toList()), inv)) cooldown = 20;
                        break;
                    case 2:
                        if (takeTherePutHere(network.stream().filter((it) -> it.mode == 1 || it.mode == 3).collect(Collectors.toList()), inv)) cooldown = 20;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private IFilteredInventory getConnectedInventory() {
        TileEntity te = world.getTileEntity(TileLayer.MAIN, x, y);
        if (te == null) return null;
        return te.getTileInventory();
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        set.addInt("mode", mode);
        set.addInt("cooldown", cooldown);
        if (filter != null) {
            DataSet f = new DataSet();
            filter.save(f);
            set.addDataSet("filter", f);
        }
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        mode = set.getInt("mode");
        cooldown = set.getInt("cooldown");
        if (set.hasKey("filter")) {
            DataSet f = set.getDataSet("filter");
            filter = new ItemFilter(new ItemInstance[9], false, false, false);
            filter.load(f);
        }
    }

    @Override
    protected boolean needsSync() {
        if (mode != lastMode) {
            lastMode = mode;
            return true;
        }
        if (lastFilter != filter) {
            lastFilter = filter;
            return true;
        }
        return cooldown > 0;
    }

    public boolean fitsFilter(ItemInstance item) {
        return filter == null || (filter.isBlacklist && !filter.contains(item)) || (!filter.isBlacklist && filter.contains(item));
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    private boolean takeHerePutThere(List<ItemConduitTileEntity> eligable, IFilteredInventory inv) {
        List<Integer> outputSlots = inv.getOutputSlots(Direction.NONE); //TODO: Support directional inventories
        for (Integer i : outputSlots) {
            ItemInstance item;
            if ((item = inv.get(i)) != null) {
                if (fitsFilter(item)) {
                    for (ItemConduitTileEntity it : eligable) {
                        IFilteredInventory otherInv = it.getConnectedInventory();
                        if (otherInv != null && it.fitsFilter(item)) {
                            List<Integer> inputSlots = otherInv.getInputSlots(item, Direction.NONE);
                            for (Integer i2 : inputSlots) {
                                ItemInstance otherItem;
                                if ((otherItem = otherInv.get(i2)) != null) {
                                    if (otherItem.isEffectivelyEqual(item)) {
                                        inv.remove(i, 1);
                                        otherInv.addToSlot(i2, item.copy().setAmount(1), false);
                                        return true;
                                    }
                                } else {
                                    inv.remove(i, 1);
                                    otherInv.addToSlot(i2, item.copy().setAmount(1), false);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean takeTherePutHere(List<ItemConduitTileEntity> eligable, IFilteredInventory inv) {
        List<Integer> inputSlots = inv.getOutputSlots(Direction.NONE); //TODO: Support directional inventories
        for (int i : inputSlots) {
            ItemInstance item = inv.get(i);
            for (ItemConduitTileEntity it : eligable) {
                IFilteredInventory otherInv = it.getConnectedInventory();
                if (otherInv != null) {
                    List<Integer> outputSlots = otherInv.getOutputSlots(Direction.NONE);
                    for (Integer i2 : outputSlots) {
                        ItemInstance otherItem;
                        if ((otherItem = otherInv.get(i2)) != null && (otherItem.isEffectivelyEqual(item) || item == null)) {
                            if (it.fitsFilter(otherItem) && fitsFilter(otherItem)) {
                                otherInv.remove(i2, 1);
                                inv.addToSlot(i, otherItem.copy().setAmount(1), false);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private List<ItemConduitTileEntity> getNetwork(List<Pos2> exclusions) { //TODO: Cache this
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
