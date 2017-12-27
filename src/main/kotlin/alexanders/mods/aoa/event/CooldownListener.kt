package alexanders.mods.aoa.event

import alexanders.mods.aoa.net.CooldownUpdatePacket
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.event.EventResult
import de.ellpeck.rockbottom.api.event.IEventListener
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent


class CooldownListener : IEventListener<EntityTickEvent> {
    override fun listen(result: EventResult, event: EntityTickEvent): EventResult {
        val entity = event.entity
        if (entity is AbstractEntityPlayer && entity.inv[entity.selectedSlot] != null) {
            if (entity.inv[entity.selectedSlot].additionalData != null) {
                val cooldown = entity.inv[entity.selectedSlot].additionalData.getInt("cooldown")
                if (cooldown > 0) {
                    entity.inv[entity.selectedSlot].additionalData.addInt("cooldown", cooldown - 1)
                    //println("set cooldown to ${cooldown -1}")
                    if (RockBottomAPI.getNet().isServer)
                        entity.sendPacket(CooldownUpdatePacket(cooldown - 1))
                }
            }
        }
        return result
    }
}
