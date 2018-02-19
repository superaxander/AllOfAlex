package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentFancyToggleButton;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.init.Resources.*;

public class FilterGui extends GuiContainer {
    private final DataSet addData;

    public FilterGui(AbstractEntityPlayer player, DataSet addData) {
        super(player, 136, 128);
        this.addData = addData;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        components.add(new ComponentFancyToggleButton(this, 34, 0, 16, 16, addData.getBoolean("isBlacklist"), () -> {
            addData.addBoolean("isBlacklist", !addData.getBoolean("isBlacklist"));
            return addData.getBoolean("isBlacklist");
        }, resourceIsBlacklist));
        components.add(new ComponentFancyToggleButton(this, 34, 17, 16, 16, addData.getBoolean("ignoreData"), () -> {
            addData.addBoolean("ignoreData", !addData.getBoolean("ignoreData"));
            return addData.getBoolean("ignoreData");
        }, resourceIgnoreData));
        components.add(new ComponentFancyToggleButton(this, 34, 34, 16, 16, addData.getBoolean("ignoreMeta"), () -> {
            addData.addBoolean("ignoreMeta", !addData.getBoolean("ignoreMeta"));
            return addData.getBoolean("ignoreMeta");
        }, resourceIgnoreMeta));
    }

    @Override
    public IResourceName getName() {
        return resourceFilterGui;
    }
}
