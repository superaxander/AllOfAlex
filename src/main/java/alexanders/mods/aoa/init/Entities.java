package alexanders.mods.aoa.init;

import alexanders.mods.aoa.entity.*;

import static alexanders.mods.aoa.init.Resources.*;
import static de.ellpeck.rockbottom.api.RockBottomAPI.ENTITY_REGISTRY;

public class Entities {
    public static void init() {
        ENTITY_REGISTRY.register(drill, EntityDrill.class);
        ENTITY_REGISTRY.register(pearlResource, PearlEntity.class);
        ENTITY_REGISTRY.register(bouncyPearlResource, BouncyPearlEntity.class);
        ENTITY_REGISTRY.register(rideablePearlResource, RideablePearlEntity.class);
        ENTITY_REGISTRY.register(miningPearlResource, MiningPearlEntity.class);
        ENTITY_REGISTRY.register(spikyPearlResource, SpikyPearlEntity.class);
        ENTITY_REGISTRY.register(bridgingPearlResource, BridgingPearlEntity.class);
        ENTITY_REGISTRY.register(bombResource, BombEntity.class);
        ENTITY_REGISTRY.register(miningBombResource, MiningBombEntity.class);
        ENTITY_REGISTRY.register(resourcePaintBomb, PaintBombEntity.class);
    }
}
