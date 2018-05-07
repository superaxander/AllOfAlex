package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import static alexanders.mods.aoa.init.Resources.bombLauncherResource;

public class BombLauncherGui extends GuiContainer {

    public BombLauncherGui(AbstractEntityPlayer player) {
        super(player, 200, 120);
        ShiftClickBehavior scb = new ShiftClickBehavior(1, player.getInv().getSlotAmount(), 0, 0);
        this.shiftClickBehaviors.add(scb);
        this.shiftClickBehaviors.add(scb.reversed());
    }

    @Override
    public ResourceName getName() {
        return bombLauncherResource;
    }
}
