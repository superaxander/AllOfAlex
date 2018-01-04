package alexanders.mods.aoa.init;

import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import static alexanders.mods.aoa.AllOfAlex.createRes;

public class Resources { // TODO: Implement this for other parts aswell? Remove this?
    public static IResourceName resourceDryFarmland;
    public static IResourceName resourceGrass;
    public static IResourceName resourceLongGrass;
    public static IResourceName resourceBlueBerryBush;
    public static IResourceName resourceRedBerryBush;
    public static IResourceName resourceUglyPlant;
    public static IResourceName resourceLargeUglyPlant;
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
    public static IResourceName resourceBricksBlue;
    public static IResourceName resourceBricksGreen;
    public static IResourceName resourceBricksGrey;
    public static IResourceName resourceBricksOrangeBrown;
    public static IResourceName resourceBricksPurple;
    public static IResourceName resourceBricksRed;
    public static IResourceName resourceBricksYellow;
    public static IResourceName resourceBricksYellowBrown;
    public static IResourceName resourceColourisedWoodAqua;
    public static IResourceName resourceColourisedWoodGoldBrown;
    public static IResourceName resourceColourisedWoodGreen;
    public static IResourceName resourceColourisedWoodGreenBrown;
    public static IResourceName resourceColourisedWoodPinkPurple;
    public static IResourceName resourceCrystalBlue;
    public static IResourceName resourceCrystalGreen;
    public static IResourceName resourceCrystalPurple;
    public static IResourceName resourceCrystalRed;
    public static IResourceName resourceCrystalYellow;
    public static IResourceName resourceSmoothStoneBlue;
    public static IResourceName resourceSmoothStoneGreen;
    public static IResourceName resourceSmoothStoneGrey;
    public static IResourceName resourceSmoothStoneOrange;
    public static IResourceName resourceSmoothStonePurple;
    public static IResourceName resourceSmoothStoneYellow;
    public static IResourceName resourcePillarHorizontalBeige;
    public static IResourceName resourcePillarVerticalBeige;
    public static IResourceName resourcePillarHorizontalBlue;
    public static IResourceName resourcePillarVerticalBlue;
    public static IResourceName resourcePillarHorizontalGreen;
    public static IResourceName resourcePillarVerticalGreen;
    public static IResourceName resourcePillarHorizontalGrey;
    public static IResourceName resourcePillarVerticalGrey;
    public static IResourceName resourcePillarHorizontalPurple;
    public static IResourceName resourcePillarVerticalPurple;
    public static IResourceName resourcePillarHorizontalPurpleBlue;
    public static IResourceName resourcePillarVerticalPurpleBlue;
    public static IResourceName resourcePlain;
    public static IResourceName resourceBrightLog;
    public static IResourceName resourceBrightLeaves;
    public static IResourceName resourceBrightSapling;
    public static IResourceName resourceAssimilatedTile;
    public static IResourceName resourceAssimilator;

    public static void init() {
        resourceDryFarmland = createRes("dry_farmland");
        resourceGrass = createRes("grass");
        resourceLongGrass = createRes("long_grass");
        resourceBlueBerryBush = createRes("blue_berry_bush");
        resourceRedBerryBush = createRes("red_berry_bush");
        resourceUglyPlant = createRes("ugly_plant");
        resourceLargeUglyPlant = createRes("large_ugly_plant");
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
        resourceBricksBlue = createRes("bricks.blue");
        resourceBricksGreen = createRes("bricks.green");
        resourceBricksGrey = createRes("bricks.grey");
        resourceBricksOrangeBrown = createRes("bricks.orange_brown");
        resourceBricksPurple = createRes("bricks.purple");
        resourceBricksRed = createRes("bricks.red");
        resourceBricksYellow = createRes("bricks.yellow");
        resourceBricksYellowBrown = createRes("bricks.yellow_brown");
        resourceColourisedWoodAqua = createRes("colourised_wood.aqua");
        resourceColourisedWoodGoldBrown = createRes("colourised_wood.gold_brown");
        resourceColourisedWoodGreen = createRes("colourised_wood.green");
        resourceColourisedWoodGreenBrown = createRes("colourised_wood.green_brown");
        resourceColourisedWoodPinkPurple = createRes("colourised_wood.pink_purple");
        resourceCrystalBlue = createRes("crystal.blue");
        resourceCrystalGreen = createRes("crystal.green");
        resourceCrystalPurple = createRes("crystal.purple");
        resourceCrystalRed = createRes("crystal.red");
        resourceCrystalYellow = createRes("crystal.yellow");
        resourceSmoothStoneBlue = createRes("smooth_stone.blue");
        resourceSmoothStoneGreen = createRes("smooth_stone.green");
        resourceSmoothStoneGrey = createRes("smooth_stone.grey");
        resourceSmoothStoneOrange = createRes("smooth_stone.orange");
        resourceSmoothStonePurple = createRes("smooth_stone.purple");
        resourceSmoothStoneYellow = createRes("smooth_stone.yellow");
        resourcePillarHorizontalBeige = createRes("pillar.horizontal.beige");
        resourcePillarVerticalBeige = createRes("pillar.vertical.beige");
        resourcePillarHorizontalBlue = createRes("pillar.horizontal.blue");
        resourcePillarVerticalBlue = createRes("pillar.vertical.blue");
        resourcePillarHorizontalGreen = createRes("pillar.horizontal.green");
        resourcePillarVerticalGreen = createRes("pillar.vertical.green");
        resourcePillarHorizontalGrey = createRes("pillar.horizontal.grey");
        resourcePillarVerticalGrey = createRes("pillar.vertical.grey");
        resourcePillarHorizontalPurple = createRes("pillar.horizontal.purple");
        resourcePillarVerticalPurple = createRes("pillar.vertical.purple");
        resourcePillarHorizontalPurpleBlue = createRes("pillar.horizontal.purple_blue");
        resourcePillarVerticalPurpleBlue = createRes("pillar.vertical.purple_blue");
        resourcePlain = createRes("plain");
        resourceAssimilatedTile = createRes("assimilated_tile");
        resourceAssimilator = createRes("assimilator");
        resourceBrightLog = createRes("bright_log");
        resourceBrightLeaves = createRes("bright_leaves");
        resourceBrightSapling = createRes("bright_sapling");
    }
}
