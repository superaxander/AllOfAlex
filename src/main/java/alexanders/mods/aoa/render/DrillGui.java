package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.entity.TileEntityDrill;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import static alexanders.mods.aoa.AllOfAlex.FIRE_COLOR;
import static alexanders.mods.aoa.AllOfAlex.createRes;

public class DrillGui extends GuiContainer {
    public final TileEntityDrill tile;

    public DrillGui(AbstractEntityPlayer player, TileEntityDrill tile) {
        super(player, 198, 180);
        this.tile = tile;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        this.components.add(new ComponentProgressBar(this, x + 74, y, 8, 18, FIRE_COLOR, true, tile::getFuelPercentage));
    }

    @Override
    public ResourceName getName() {
        return createRes("drill_gui");
    }
}
