package alexanders.mods.aoa.init;

import alexanders.mods.aoa.event.*;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.EntityDamageEvent;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.GuiOpenEvent;
import de.ellpeck.rockbottom.api.event.impl.ResetMovedPlayerEvent;

public class Events {
    public static void init(IGameInstance game, IEventHandler eventHandler) {
        if (!game.isDedicatedServer()) {
            eventHandler.registerListener(EntityTickEvent.class, new InputListener());
        }
        eventHandler.registerListener(EntityDamageEvent.class, new DamageNegator());
        eventHandler.registerListener(ResetMovedPlayerEvent.class, new MovementAllower());
        eventHandler.registerListener(EntityTickEvent.class, new CooldownListener());
    }
}
