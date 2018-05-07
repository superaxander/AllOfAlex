package alexanders.mods.aoa.event;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventListener;
import de.ellpeck.rockbottom.api.event.impl.ResetMovedPlayerEvent;

import static alexanders.mods.aoa.init.Resources.EFFECT_JUMP_ACTIVE;
import static alexanders.mods.aoa.init.Resources.EFFECT_SPEED_ACTIVE;

public class MovementAllower implements IEventListener<ResetMovedPlayerEvent> { //Awful name I know
    @Override
    public EventResult listen(EventResult result, ResetMovedPlayerEvent event) {
        ModBasedDataSet data = event.player.getAdditionalData();
        if (data != null) {
            int speedLeft;
            if ((speedLeft = data.getInt(EFFECT_SPEED_ACTIVE)) > 0) {
                event.allowedDefaultDistance += 0.1 * (250 - speedLeft);
            }
            if (data.getInt(EFFECT_JUMP_ACTIVE) > 0) {
                event.allowedDefaultDistance += 1.5;
            }
        }
        return result;
    }
}
