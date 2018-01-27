package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.entity.CannonTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.PROGRESS_COLOR;
import static alexanders.mods.aoa.init.Resources.resourceCannonGui;

public class CannonGui extends GuiContainer {
    private final CannonTileEntity te;

    public CannonGui(AbstractEntityPlayer player, CannonTileEntity te) {
        super(player, 200, 120);
        this.te = te;
        ShiftClickBehavior scb = new ShiftClickBehavior(1, player.getInv().getSlotAmount(), 0, 0);
        this.shiftClickBehaviors.add(scb);
        this.shiftClickBehaviors.add(scb.reversed());
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        this.components.add(new ComponentProgressBar(this, 78, 0, 10, 18, PROGRESS_COLOR, true, () -> te.cooldown / (float) te.maxCooldown));
    }

    @Override
    public IResourceName getName() {
        return resourceCannonGui;
    }
}
