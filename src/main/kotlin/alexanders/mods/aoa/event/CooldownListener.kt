package alexanders.mods.aoa.event

import alexanders.mods.aoa.item.ICooldownable
import alexanders.mods.aoa.net.CooldownUpdatePacket
import de.ellpeck.rockbottom.api.RockBottomAPI
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer
import de.ellpeck.rockbottom.api.event.EventResult
import de.ellpeck.rockbottom.api.event.IEventListener
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent


class CooldownListener : IEventListener<EntityTickEvent> {
    override fun listen(result: EventResult, event: EntityTickEvent): EventResult {
        val entity = event.entity
        if (entity is AbstractEntityPlayer) {
            for (i in 0 until 10) {
                if (entity.inv[i] != null && entity.inv[i].item is ICooldownable)
                    if (entity.inv[i].additionalData != null) {
                        val cooldown = entity.inv[i].additionalData.getInt("cooldown")
                        if (cooldown > 0) {
                            entity.inv[i].additionalData.addInt("cooldown", cooldown - 1)
                            //println("set cooldown to ${cooldown -1}")
                            if (RockBottomAPI.getNet().isServer)
                                entity.sendPacket(CooldownUpdatePacket(cooldown - 1, i))
                        }
                }
            }
        }
        return result
    }
}
