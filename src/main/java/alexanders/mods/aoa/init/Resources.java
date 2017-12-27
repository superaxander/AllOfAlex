package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class Resources { // TODO: Implement this for other parts aswell? Remove this?
    public static IResourceName dryFarmland;
    public static IResourceName grass;
    public static IResourceName longGrass;
    public static IResourceName blueBerryBush;
    public static IResourceName redBerryBush;
    public static IResourceName uglyPlant;
    public static IResourceName largeUglyPlant;
    public static IResourceName shears;
    public static IResourceName drill;
    public static IResourceName drill_basic;
    public static IResourceName drill_copper;
    public static IResourceName drill_bit_basic;
    public static IResourceName drill_bit_copper;
    public static IResourceName PEARL_RESOURCE;
    public static IResourceName BOUNCY_PEARL_RESOURCE;
    public static IResourceName RIDEABLE_PEARL_RESOURCE;
    public static IResourceName MINING_PEARL_RESOURCE;
    public static IResourceName SPIKY_PEARL_RESOURCE;
    public static IResourceName SPAWN_PEARL_RESOURCE;
    public static IResourceName WAYPOINT_PEARL_RESOURCE;
    public static IResourceName BRIDGING_PEARL_RESOURCE;
    public static IResourceName COOLDOWN_RESOURCE;
    public static IResourceName PEARL_DESC_RESOURCE;
    public static IResourceName BOUNCY_PEARL_DESC_RESOURCE;
    public static IResourceName RIDEABLE_PEARL_DESC_RESOURCE;
    public static IResourceName MINING_PEARL_DESC_RESOURCE;
    public static IResourceName SPIKY_PEARL_DESC_RESOURCE;
    public static IResourceName SPAWN_PEARL_DESC_RESOURCE;
    public static IResourceName WAYPOINT_PEARL_DESC_RESOURCE;
    public static IResourceName WAYPOINT_PEARL_USAGE_DESC_RESOURCE;
    public static IResourceName BRIDGING_PEARL_DESC_RESOURCE;
    public static IResourceName SET_TO_DESC_RESOURCE;
    public static IResourceName MORE_INFO_DESC_RESOURCE;
    public static IResourceName PHANTOM_TILE_RESOURCE;
    public static IResourceName PEARL_ORE_RESOURCE;
    public static IResourceName SLIME_RESOURCE;
    public static IResourceName TELEPORTATION_PARTICLE_RESOURCE;
    public static IResourceName BLOOD_PARTICLE_RESOURCE;
    public static IResourceName UP_ARROW;
    public static IResourceName DOWN_ARROW;

    public static void init() {
        dryFarmland = createRes("dry_farmland");
        grass = createRes("grass");
        longGrass = createRes("long_grass");
        blueBerryBush = createRes("blue_berry_bush");
        redBerryBush = createRes("red_berry_bush");
        uglyPlant = createRes("ugly_plant");
        largeUglyPlant = createRes("large_ugly_plant");
        shears = createRes("shears");
        drill = createRes("drill"); // Only used for entity registration
        drill_basic = createRes("drill_basic");
        drill_copper = createRes("drill_copper");
        drill_bit_basic = createRes("drill_bit_basic");
        drill_bit_copper = createRes("drill_bit_copper");
        PEARL_RESOURCE = createRes("pearl");
        BOUNCY_PEARL_RESOURCE = createRes("bouncy_pearl");
        RIDEABLE_PEARL_RESOURCE = createRes("rideable_pearl");
        MINING_PEARL_RESOURCE = createRes("mining_pearl");
        SPIKY_PEARL_RESOURCE = createRes("spiky_pearl");
        SPAWN_PEARL_RESOURCE = createRes("spawn_pearl");
        WAYPOINT_PEARL_RESOURCE = createRes("waypoint_pearl");
        BRIDGING_PEARL_RESOURCE = createRes("bridging_pearl");
        COOLDOWN_RESOURCE = createRes("cooldown");
        PEARL_DESC_RESOURCE = createRes("desc.pearl");
        BOUNCY_PEARL_DESC_RESOURCE = createRes("desc.bouncy_pearl");
        RIDEABLE_PEARL_DESC_RESOURCE = createRes("desc.rideable_pearl");
        MINING_PEARL_DESC_RESOURCE = createRes("desc.mining_pearl");
        SPIKY_PEARL_DESC_RESOURCE = createRes("desc.spiky_pearl");
        SPAWN_PEARL_DESC_RESOURCE = createRes("desc.spawn_pearl");
        WAYPOINT_PEARL_DESC_RESOURCE = createRes("desc.waypoint_pearl");
        WAYPOINT_PEARL_USAGE_DESC_RESOURCE = createRes("desc.waypoint_pearl_usage");
        BRIDGING_PEARL_DESC_RESOURCE = createRes("desc.bridging_pearl");
        SET_TO_DESC_RESOURCE = createRes("desc.set_to");
        MORE_INFO_DESC_RESOURCE = createRes("desc.more_info");
        PHANTOM_TILE_RESOURCE = createRes("phantom");
        PEARL_ORE_RESOURCE = createRes("pearl_ore");
        SLIME_RESOURCE = createRes("slime");
        TELEPORTATION_PARTICLE_RESOURCE = createRes("particles.teleportation");
        BLOOD_PARTICLE_RESOURCE = createRes("particles.blood");
        UP_ARROW = createRes("button.up");
        DOWN_ARROW = createRes("button.down");
    }
}
