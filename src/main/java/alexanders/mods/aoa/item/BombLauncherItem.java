package alexanders.mods.aoa.item;

import alexanders.mods.aoa.init.Keys;
import alexanders.mods.aoa.net.FireBombPacket;
import alexanders.mods.aoa.render.BombLauncherGui;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getGame;
import static de.ellpeck.rockbottom.api.RockBottomAPI.getNet;

public class BombLauncherItem extends ItemBasic {
    private static final ResourceName LAST_USE = createRes("lastUse");
    private static final ResourceName INVENTORY = createRes("inventory");
    
    public BombLauncherItem(ResourceName name) {
        super(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        long lastUse = instance.getOrCreateAdditionalData().getLong(LAST_USE);
        Inventory i = new Inventory(1);
        i.addChangeCallback((inv, i2) -> {
            DataSet d = new DataSet();
            i.save(d);
            instance.getAdditionalData().addDataSet(INVENTORY, d);
        });
        ModBasedDataSet a_d;
        if ((a_d = instance.getAdditionalData()) != null) i.load(a_d.getDataSet(INVENTORY));
        if (getNet().isClient() || !getNet().isActive()) {
            if (Keys.KEY_OPEN_BOMB_LAUNCHER_INVENTORY.isDown()) {
                player.openGuiContainer(new BombLauncherGui(player), new BombLauncherContainer(player, player.getInv(), i));
                return true;
            } else {
                if (lastUse == 0 || System.currentTimeMillis() - lastUse > 1000) { // TODO: Use ticks for this
                    instance.getAdditionalData().addLong(LAST_USE, System.currentTimeMillis());
                    if (i.get(0) != null) {

                        if (getNet().isClient()) getNet().sendToServer(new FireBombPacket(player.x, player.y, Useable.Companion.angle(),
                                                                                          i.get(0).getItem() instanceof MiningBombItem ? 0 :
                                                                                                  i.get(0).getItem() instanceof PaintBombItem ? 2 : 1));
                        else new FireBombPacket(player.x, player.y, Useable.Companion.angle(),
                                                i.get(0).getItem() instanceof MiningBombItem ? 0 : i.get(0).getItem() instanceof PaintBombItem ? 2 : 1).handle(getGame(), null);
                        i.remove(0, 1);
                    }
                    return false;
                }
            }
        } else {
            if (getGame().isDedicatedServer() || Keys.KEY_OPEN_BOMB_LAUNCHER_INVENTORY.isDown()) {
                player.openGuiContainer(new BombLauncherGui(player), new BombLauncherContainer(player, player.getInv(), i));
                return true;
            } else {
                if (lastUse == 0 || System.currentTimeMillis() - lastUse > 1000) { // TODO: Use ticks for this
                    instance.getAdditionalData().addLong(LAST_USE, System.currentTimeMillis());
                    if (i.get(0) != null) {

                        if (getNet().isClient()) getNet().sendToServer(new FireBombPacket(player.x, player.y, Useable.Companion.angle(),
                                                                                          i.get(0).getItem() instanceof MiningBombItem ? 0 :
                                                                                                  i.get(0).getItem() instanceof PaintBombItem ? 2 : 1));
                        else new FireBombPacket(player.x, player.y, Useable.Companion.angle(),
                                                i.get(0).getItem() instanceof MiningBombItem ? 0 : i.get(0).getItem() instanceof PaintBombItem ? 2 : 1).handle(getGame(), null);
                        i.remove(0, 1);
                    }
                    return false;
                }
                return false;
            }
        }
        return true;
    }
}
