package alexanders.mods.aoa.render;

import alexanders.mods.aoa.AllOfAlex;
import alexanders.mods.aoa.tile.entity.AssimilatorTileEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceAssimilator;

public class AssimilatorGui extends GuiContainer {
    private final AssimilatorTileEntity te;

    public AssimilatorGui(AbstractEntityPlayer player, AssimilatorTileEntity te) {
        super(player, 200, 140);
        this.te = te;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        components.add(new ComponentProgressBar(this, 65, 4, 70, 12, AllOfAlex.PROGRESS_COLOR, false, te::getProgressBar));
    }

    @Override
    public IResourceName getName() {
        return resourceAssimilator;
    }
}
