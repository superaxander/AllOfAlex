package alexanders.mods.aoa.item;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class AssimilatedItemTile extends ColourableItemTile {

    public AssimilatedItemTile(IResourceName name) {
        super(name);
    }

    @Override
    public boolean isDataSensitive(ItemInstance instance) {
        return true;
    }
}
