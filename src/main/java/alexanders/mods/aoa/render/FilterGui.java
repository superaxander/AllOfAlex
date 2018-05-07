package alexanders.mods.aoa.render;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentFancyToggleButton;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;
import static alexanders.mods.aoa.init.Resources.*;

public class FilterGui extends GuiContainer {
    private static final ResourceName IS_BLACKLIST = createRes("isBlacklist");
    private static final ResourceName IGNORE_DATA = createRes("ignoreData");
    private static final ResourceName IGNORE_META = createRes("ignoreMeta");
    
    private final ModBasedDataSet addData;

    public FilterGui(AbstractEntityPlayer player, ModBasedDataSet addData) {
        super(player, 136, 128);
        this.addData = addData;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);
        components.add(new ComponentFancyToggleButton(this, 34, 0, 16, 16, addData.getBoolean(IS_BLACKLIST), () -> {
            addData.addBoolean(IS_BLACKLIST, !addData.getBoolean(IS_BLACKLIST));
            return addData.getBoolean(IS_BLACKLIST);
        }, resourceIsBlacklist));
        components.add(new ComponentFancyToggleButton(this, 34, 17, 16, 16, addData.getBoolean(IGNORE_DATA), () -> {
            addData.addBoolean(IGNORE_DATA, !addData.getBoolean(IGNORE_DATA));
            return addData.getBoolean(IGNORE_DATA);
        }, resourceIgnoreData));
        components.add(new ComponentFancyToggleButton(this, 34, 34, 16, 16, addData.getBoolean(IGNORE_META), () -> {
            addData.addBoolean(IGNORE_META, !addData.getBoolean(IGNORE_META));
            return addData.getBoolean(IGNORE_META);
        }, resourceIgnoreMeta));
    }

    @Override
    public ResourceName getName() {
        return resourceFilterGui;
    }
}
