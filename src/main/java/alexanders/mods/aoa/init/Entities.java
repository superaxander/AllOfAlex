package alexanders.mods.aoa.init;

import alexanders.mods.aoa.entity.*;

import static alexanders.mods.aoa.init.Resources.*;
import static de.ellpeck.rockbottom.api.RockBottomAPI.ENTITY_REGISTRY;

public class Entities {
    public static void init() {
        ENTITY_REGISTRY.register(drill, EntityDrill.class);
        ENTITY_REGISTRY.register(PEARL_RESOURCE, PearlEntity.class);
        ENTITY_REGISTRY.register(BOUNCY_PEARL_RESOURCE, BouncyPearlEntity.class);
        ENTITY_REGISTRY.register(RIDEABLE_PEARL_RESOURCE, RideablePearlEntity.class);
        ENTITY_REGISTRY.register(MINING_PEARL_RESOURCE, MiningPearlEntity.class);
        ENTITY_REGISTRY.register(SPIKY_PEARL_RESOURCE, SpikyPearlEntity.class);
        ENTITY_REGISTRY.register(BRIDGING_PEARL_RESOURCE, BridgingPearlEntity.class);
    }
}
