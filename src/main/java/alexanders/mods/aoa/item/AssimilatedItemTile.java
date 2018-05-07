package alexanders.mods.aoa.item;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class AssimilatedItemTile extends ColourableItemTile {

    public AssimilatedItemTile(ResourceName name) {
        super(name);
    }

    @Override
    public boolean isDataSensitive(ItemInstance instance) {
        return true;
    }
}
