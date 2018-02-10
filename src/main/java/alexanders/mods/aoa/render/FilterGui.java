package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.resourceFilterGui;

public class FilterGui extends GuiContainer{

    public FilterGui(AbstractEntityPlayer player) {
        super(player, 180, 140);
    }

    @Override
    public IResourceName getName() {
        return resourceFilterGui;
    }
}
