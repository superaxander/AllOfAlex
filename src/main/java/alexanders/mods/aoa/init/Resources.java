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
    public static IResourceName pearlResource;
    public static IResourceName bouncyPearlResource;
    public static IResourceName rideablePearlResource;
    public static IResourceName miningPearlResource;
    public static IResourceName spikyPearlResource;
    public static IResourceName spawnPearlResource;
    public static IResourceName waypointPearlResource;
    public static IResourceName bridgingPearlResource;
    public static IResourceName cooldownResource;
    public static IResourceName pearlDescResource;
    public static IResourceName bouncyPearlDescResource;
    public static IResourceName rideablePearlDescResource;
    public static IResourceName miningPearlDescResource;
    public static IResourceName spikyPearlDescResource;
    public static IResourceName spawnPearlDescResource;
    public static IResourceName waypointPearlDescResource;
    public static IResourceName waypointPearlUsageDescResource;
    public static IResourceName bridgingPearlDescResource;
    public static IResourceName setToDescResource;
    public static IResourceName moreInfoDescResource;
    public static IResourceName phantomTileResource;
    public static IResourceName pearlOreResource;
    public static IResourceName slimeResource;
    public static IResourceName teleportationParticleResource;
    public static IResourceName bloodParticleResource;
    public static IResourceName bombResource;
    public static IResourceName miningBombResource;
    public static IResourceName bombLauncherResource;

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
        pearlResource = createRes("pearl");
        bouncyPearlResource = createRes("bouncy_pearl");
        rideablePearlResource = createRes("rideable_pearl");
        miningPearlResource = createRes("mining_pearl");
        spikyPearlResource = createRes("spiky_pearl");
        spawnPearlResource = createRes("spawn_pearl");
        waypointPearlResource = createRes("waypoint_pearl");
        bridgingPearlResource = createRes("bridging_pearl");
        cooldownResource = createRes("cooldown");
        pearlDescResource = createRes("desc.pearl");
        bouncyPearlDescResource = createRes("desc.bouncy_pearl");
        rideablePearlDescResource = createRes("desc.rideable_pearl");
        miningPearlDescResource = createRes("desc.mining_pearl");
        spikyPearlDescResource = createRes("desc.spiky_pearl");
        spawnPearlDescResource = createRes("desc.spawn_pearl");
        waypointPearlDescResource = createRes("desc.waypoint_pearl");
        waypointPearlUsageDescResource = createRes("desc.waypoint_pearl_usage");
        bridgingPearlDescResource = createRes("desc.bridging_pearl");
        setToDescResource = createRes("desc.set_to");
        moreInfoDescResource = createRes("desc.more_info");
        phantomTileResource = createRes("phantom");
        pearlOreResource = createRes("pearl_ore");
        slimeResource = createRes("slime");
        teleportationParticleResource = createRes("particles.teleportation");
        bloodParticleResource = createRes("particles.blood");
        bombResource = createRes("bomb");
        miningBombResource = createRes("mining_bomb");
        bombLauncherResource = createRes("bomb_launcher");
    }
}
