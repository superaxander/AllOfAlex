package alexanders.mods.aoa.event;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventListener;
import de.ellpeck.rockbottom.api.event.impl.EntityDamageEvent;

public class DamageNegator implements IEventListener<EntityDamageEvent> {
    @Override
    public EventResult listen(EventResult result, EntityDamageEvent event) {
        if (event.entity.isFalling && event.entity.collidedVert) {
            DataSet additionalSet;
            if ((additionalSet = event.entity.getAdditionalData()) != null) {
                if (additionalSet.getInt("effectJumpActive") > 0) {
                    return EventResult.CANCELLED;
                }
            }
        }
        return result;
    }
}
