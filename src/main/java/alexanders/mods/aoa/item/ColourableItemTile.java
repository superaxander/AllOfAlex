package alexanders.mods.aoa.item;

import alexanders.mods.aoa.Colours;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ColourableItemTile extends ItemTile {
    public ColourableItemTile(IResourceName name) {
        super(name);
    }

    @Override
    public IResourceName getUnlocalizedName(ItemInstance instance) {
        return super.getUnlocalizedName(instance).addSuffix("." + Colours.get(instance.getMeta()).name().toLowerCase());
    }

    @Override
    public int getHighestPossibleMeta() {
        return Colours.values().length - 1;
    }
}
