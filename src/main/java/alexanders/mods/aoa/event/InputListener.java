package alexanders.mods.aoa.event;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.EntityLiving;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventListener;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;

import static de.ellpeck.rockbottom.api.data.settings.Settings.*;

public class InputListener implements IEventListener<EntityTickEvent> {
    @Override
    public EventResult listen(EventResult result, EntityTickEvent event) {
        if (KEY_LEFT.isDown() || KEY_RIGHT.isDown()) {
            DataSet additionalData;
            if ((additionalData = event.entity.getAdditionalData()) != null) {
                int ticksRemaining;
                if ((ticksRemaining = additionalData.getInt("effectSpeedActive")) > 0) {
                    additionalData.addInt("effectSpeedActive", ticksRemaining - 1);
                    if (event.entity.facing.x > 0) {// && event.entity.motionX >= lastMotionX) {
                        event.entity.motionX += 0.5;
                    } else if (event.entity.facing.x < 0) {// && event.entity.motionX <= lastMotionX) {
                        event.entity.motionX -= 0.5;
                    }
                }
            }
        }
        if (KEY_JUMP.isDown()) {
            DataSet additionalData;
            if ((additionalData = event.entity.getAdditionalData()) != null) {
                if (additionalData.getInt("effectJumpActive") > 0) {
                    if (event.entity instanceof EntityLiving) {
                        ((EntityLiving) event.entity).jumping = false;
                        ((EntityLiving) event.entity).jump(.5);
                    }
                }
            }

        }
        DataSet additionalData;
        if ((additionalData = event.entity.getAdditionalData()) != null) {
            int ticksRemaining;
            if ((ticksRemaining = additionalData.getInt("effectJumpActive")) > 0) {
                additionalData.addInt("effectJumpActive", ticksRemaining - 1);
            }

            if ((ticksRemaining = additionalData.getInt("effectSpeedActive")) > 0) {
                additionalData.addInt("effectSpeedActive", ticksRemaining - 1);
            }
        }
        return result;
    }
}
