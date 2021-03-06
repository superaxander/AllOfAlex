package alexanders.mods.aoa.init;

import alexanders.mods.aoa.net.*;

import static de.ellpeck.rockbottom.api.RockBottomAPI.PACKET_REGISTRY;

public class Packets {
    public static void init() {
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), CannonRightClickPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), RotatePacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), ItemUsePacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), EntityPositionUpdatePacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), CooldownUpdatePacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), BloodPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), FireBombPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), PaintBombParticlePacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), NotePlayPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), DataCraftingPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), RemoveFilterPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), JukeboxStartPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), JukeboxStopPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), JukeboxSyncPacket.class);
        PACKET_REGISTRY.register(PACKET_REGISTRY.getNextFreeId(), URLUpdatePacket.class);
    }
}
