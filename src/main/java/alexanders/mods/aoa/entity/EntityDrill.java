package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.AllOfAlex;
import alexanders.mods.aoa.render.DrillContainer;
import alexanders.mods.aoa.render.DrillGui;
import alexanders.mods.aoa.render.DrillRenderer;
import alexanders.mods.aoa.tile.entity.TileEntityDrill;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public class EntityDrill extends Entity {
    public ResourceName name;
    private DrillRenderer renderer;
    private TileEntityDrill tileEntity;
    private float breakProgress;

    public EntityDrill(IWorld world, ResourceName name, int x, int y) {
        super(world);
        renderer = new DrillRenderer(name.addSuffix(".active"));
        this.name = name;
        tileEntity = (TileEntityDrill) world.getTileEntity(x + 1, y);
        if (tileEntity == null) tileEntity = new TileEntityDrill(world, 0, 0, TileLayer.MAIN);
        tileEntity.isContained = true;
        tileEntity.entity = this;
        world.removeTileEntity(TileLayer.MAIN, x + 1, y);
        this.setPos(x, y);
        this.breakProgress = 0;
        this.currentBounds.set(0, 0, 3, 4);
    }

    public EntityDrill(IWorld world) {
        super(world);
        tileEntity = new TileEntityDrill(world, 0, 0, TileLayer.MAIN);
        tileEntity.isContained = true;
        tileEntity.entity = this;
    }

    @Override
    public void save(DataSet set) {
        super.save(set);
        set.addString("resourceName", name.getDomain() + "/" + name.getResourceName());
        DataSet te = new DataSet();
        tileEntity.save(te, false);
        set.addDataSet("te", te);
        set.addFloat("breakProgress", breakProgress);
    }

    @Override
    public void load(DataSet set) {
        super.load(set);
        name = new ResourceName(set.getString("resourceName"));
        renderer = new DrillRenderer(name.addSuffix(".active"));
        tileEntity = new TileEntityDrill(world, 0, 0, TileLayer.MAIN); // The location doesn't matter since we update when we stop moving
        tileEntity.isContained = true;
        tileEntity.entity = this;
        tileEntity.load(set.getDataSet("te"), false);
        breakProgress = set.getFloat("breakProgress");
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        if (tileEntity != null) {
            tileEntity.isContained = true;
            tileEntity.entity = this; //TODO: Remove the many duplicates of this
            this.tileEntity.update(game);
            if (this.tileEntity.isActive()) {
                for (int x = 0; x < 3; x++) {
                    int x2 = (int) (this.x + .5 * (this.x < 0 ? -1 : 1)) + x;
                    int y2 = (int) Math.round(this.y) - 1;
                    Tile tile = world.getState(TileLayer.MAIN, x2, y2).getTile();
                    if (tile.isAir()) continue;
                    if ((breakProgress += tileEntity.tilesPerTick) >= 1f) {
                        breakProgress -= 1f;

                        if (canMineTile(y2, tile.getHardness(world, x2, y2, TileLayer.MAIN))) {
                            tile.doBreak(world, x2, y2, TileLayer.MAIN, null, false, false);
                            List<ItemInstance> drops = tile.getDrops(world, x2, y2, TileLayer.MAIN, null);
                            if (drops != null) drops.forEach(drop -> {
                                if (drop != null) {
                                    if (tileEntity.fuelInventory.get(0) != null)
                                        if (drop.getItem() == tileEntity.fuelInventory.get(0).getItem()) drop = tileEntity.fuelInventory.add(drop, true);
                                    if (drop != null) tileEntity.inventory.add(drop, false);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    private boolean canMineTile(int y, float hardness) {
        return y > 0 || (hardness + Math.pow(-y, .67) - tileEntity.maxHardness) < 0;
    }

    @Override
    public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY) {
        if (tileEntity != null) {
            player.openGuiContainer(new DrillGui(player, tileEntity), new DrillContainer(player, tileEntity, tileEntity.fuelInventory, tileEntity.inventory, player.getInv()));
            return true;
        } else {
            AllOfAlex.instance.logger.severe("The drill's tile entity was null! Please report");
            return false;
        }
    }

    @Override
    public IEntityRenderer getRenderer() {
        return renderer;
    }
}
