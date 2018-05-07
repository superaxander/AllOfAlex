package alexanders.mods.aoa.event;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.EntityLiving;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventListener;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;

import static alexanders.mods.aoa.init.Resources.EFFECT_JUMP_ACTIVE;
import static alexanders.mods.aoa.init.Resources.EFFECT_SPEED_ACTIVE;
import static de.ellpeck.rockbottom.api.data.settings.Settings.*;

public class InputListener implements IEventListener<EntityTickEvent> {
    @Override
    public EventResult listen(EventResult result, EntityTickEvent event) {
        if (KEY_LEFT.isDown() || KEY_RIGHT.isDown()) {
            ModBasedDataSet additionalData;
            if ((additionalData = event.entity.getAdditionalData()) != null) {
                int ticksRemaining;
                if ((ticksRemaining = additionalData.getInt(EFFECT_SPEED_ACTIVE)) > 0) {
                    additionalData.addInt(EFFECT_SPEED_ACTIVE, ticksRemaining - 1);
                    if (event.entity.facing.x > 0) {// && event.entity.motionX >= lastMotionX) {
                        event.entity.motionX += 0.5;
                    } else if (event.entity.facing.x < 0) {// && event.entity.motionX <= lastMotionX) {
                        event.entity.motionX -= 0.5;
                    }
                }
            }
        }
        if (KEY_JUMP.isDown()) {
            ModBasedDataSet additionalData;
            if ((additionalData = event.entity.getAdditionalData()) != null) {
                if (additionalData.getInt(EFFECT_JUMP_ACTIVE) > 0) {
                    if (event.entity instanceof EntityLiving) {
                        ((EntityLiving) event.entity).jumping = false;
                        ((EntityLiving) event.entity).jump(.5);
                    }
                }
            }

        }
        ModBasedDataSet additionalData;
        if ((additionalData = event.entity.getAdditionalData()) != null) {
            int ticksRemaining;
            if ((ticksRemaining = additionalData.getInt(EFFECT_JUMP_ACTIVE)) > 0) {
                additionalData.addInt(EFFECT_JUMP_ACTIVE, ticksRemaining - 1);
            }

            if ((ticksRemaining = additionalData.getInt(EFFECT_SPEED_ACTIVE)) > 0) {
                additionalData.addInt(EFFECT_SPEED_ACTIVE, ticksRemaining - 1);
            }
        }
        return result;
    }
}
