package alexanders.mods.aoa.event;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventListener;
import de.ellpeck.rockbottom.api.event.impl.EntityDamageEvent;

import static alexanders.mods.aoa.init.Resources.EFFECT_JUMP_ACTIVE;

public class DamageNegator implements IEventListener<EntityDamageEvent> {
    @Override
    public EventResult listen(EventResult result, EntityDamageEvent event) {
        if (event.entity.isFalling && event.entity.collidedVert) {
            ModBasedDataSet additionalSet;
            if ((additionalSet = event.entity.getAdditionalData()) != null) {
                if (additionalSet.getInt(EFFECT_JUMP_ACTIVE) > 0) {
                    return EventResult.CANCELLED;
                }
            }
        }
        return result;
    }
}
