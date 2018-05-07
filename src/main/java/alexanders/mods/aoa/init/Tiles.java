package alexanders.mods.aoa.init;

import alexanders.mods.aoa.tile.*;
import de.ellpeck.rockbottom.api.tile.TileBasic;

import static alexanders.mods.aoa.init.Resources.*;

public class Tiles {
    public static ItemCannonTile itemCannon;
    public static FunnelTile funnelTile;
    public static VariantTile dryFarmland;
    public static VariantTile grass;
    public static VariantTile longGrass;
    public static VariantTile blueBerryBush;
    public static VariantTile redBerryBush;
    public static VariantTile uglyPlant;
    public static VariantTile largeUglyPlant;
    public static TileDrill drillBasic;
    public static TileDrill drillCopper;
    public static PhantomTile phantomTile;
    public static PearlOreTile pearlOre;
    public static SlimeTile slime;
    public static TileBasic bricksBlue;
    public static TileBasic bricksGreen;
    public static TileBasic bricksGrey;
    public static TileBasic bricksOrangeBrown;
    public static TileBasic bricksPurple;
    public static TileBasic bricksRed;
    public static TileBasic bricksYellow;
    public static TileBasic bricksYellowBrown;
    public static TileBasic colourisedWoodAqua;
    public static TileBasic colourisedWoodGoldBrown;
    public static TileBasic colourisedWoodGreen;
    public static TileBasic colourisedWoodGreenBrown;
    public static TileBasic colourisedWoodPinkPurple;
    public static TileBasic crystalBlue;
    public static TileBasic crystalGreen;
    public static TileBasic crystalPurple;
    public static TileBasic crystalRed;
    public static TileBasic crystalYellow;
    public static TileBasic smoothStoneBlue;
    public static TileBasic smoothStoneGreen;
    public static TileBasic smoothStoneGrey;
    public static TileBasic smoothStoneOrange;
    public static TileBasic smoothStonePurple;
    public static TileBasic smoothStoneYellow;
    public static TileBasic pillarHorizontalBeige;
    public static TileBasic pillarVerticalBeige;
    public static TileBasic pillarHorizontalBlue;
    public static TileBasic pillarVerticalBlue;
    public static TileBasic pillarHorizontalGreen;
    public static TileBasic pillarVerticalGreen;
    public static TileBasic pillarHorizontalGrey;
    public static TileBasic pillarVerticalGrey;
    public static TileBasic pillarHorizontalPurple;
    public static TileBasic pillarVerticalPurple;
    public static TileBasic pillarHorizontalPurpleBlue;
    public static TileBasic pillarVerticalPurpleBlue;
    public static BrightLogTile brightLog;
    public static BrightLeavesTile brightLeaves;
    public static BrightSapling brightSapling;
    public static ColourableTile plain;
    public static AssimilatedTile assimilatedTile;
    public static AssimilatorTile assimilator;
    public static ItemConduitTile itemConduit;
    public static ColourableTile glass;
    public static TileBasic sunBlue;
    public static TileBasic sunGreen;
    public static TileBasic sunOrange;
    public static TileBasic sunPurple;
    public static TileBasic sunRed;
    public static TileBasic sunWhiteBlue;
    public static TileBasic sunYellowBlue;
    public static BombCannonTile bombCannon;
    public static NoteTile noteTile;
    public static JukeboxTile jukebox;

    public static void init() {
        itemCannon = new ItemCannonTile(resourceItemCannon);
        funnelTile = new FunnelTile(resourceFunnel);
        FoliageAssets.dry_farmland.tile = dryFarmland = new VariantTile(resourceDryFarmland);
        FoliageAssets.grass.tile = grass = new VariantTile(resourceGrass);
        FoliageAssets.long_grass.tile = longGrass = new VariantTile(resourceLongGrass);
        FoliageAssets.blue_berry_bush.tile = blueBerryBush = new BerryBushTile(resourceBlueBerryBush);
        FoliageAssets.red_berry_bush.tile = redBerryBush = new BerryBushTile(resourceRedBerryBush);
        FoliageAssets.ugly_plant.tile = uglyPlant = new VariantTile(resourceUglyPlant);
        FoliageAssets.large_ugly_plant.tile = largeUglyPlant = new VariantTile(resourceLargeUglyPlant);
        drillBasic = new TileDrill(Resources.drill_basic, 30, .25f, .5f, 10);
        drillCopper = new TileDrill(Resources.drill_copper, 42, .5f, .25f, 20);
        phantomTile = new PhantomTile();
        pearlOre = new PearlOreTile();
        slime = new SlimeTile();

        bricksBlue = new TileBasic(resourceBricksBlue);
        bricksGreen = new TileBasic(resourceBricksGreen);
        bricksGrey = new TileBasic(resourceBricksGrey);
        bricksOrangeBrown = new TileBasic(resourceBricksOrangeBrown);
        bricksPurple = new TileBasic(resourceBricksPurple);
        bricksRed = new TileBasic(resourceBricksRed);
        bricksYellow = new TileBasic(resourceBricksYellow);
        bricksYellowBrown = new TileBasic(resourceBricksYellowBrown);
        colourisedWoodAqua = new TileBasic(resourceColourisedWoodAqua);
        colourisedWoodGoldBrown = new TileBasic(resourceColourisedWoodGoldBrown);
        colourisedWoodGreen = new TileBasic(resourceColourisedWoodGreen);
        colourisedWoodGreenBrown = new TileBasic(resourceColourisedWoodGreenBrown);
        colourisedWoodPinkPurple = new TileBasic(resourceColourisedWoodPinkPurple);
        crystalBlue = new TileBasic(resourceCrystalBlue);
        crystalGreen = new TileBasic(resourceCrystalGreen);
        crystalPurple = new TileBasic(resourceCrystalPurple);
        crystalRed = new TileBasic(resourceCrystalRed);
        crystalYellow = new TileBasic(resourceCrystalYellow);
        smoothStoneBlue = new TileBasic(resourceSmoothStoneBlue);
        smoothStoneGreen = new TileBasic(resourceSmoothStoneGreen);
        smoothStoneGrey = new TileBasic(resourceSmoothStoneGrey);
        smoothStoneOrange = new TileBasic(resourceSmoothStoneOrange);
        smoothStonePurple = new TileBasic(resourceSmoothStonePurple);
        smoothStoneYellow = new TileBasic(resourceSmoothStoneYellow);
        pillarHorizontalBeige = new TileBasic(resourcePillarHorizontalBeige);
        pillarVerticalBeige = new TileBasic(resourcePillarVerticalBeige);
        pillarHorizontalBlue = new TileBasic(resourcePillarHorizontalBlue);
        pillarVerticalBlue = new TileBasic(resourcePillarVerticalBlue);
        pillarHorizontalGreen = new TileBasic(resourcePillarHorizontalGreen);
        pillarVerticalGreen = new TileBasic(resourcePillarVerticalGreen);
        pillarHorizontalGrey = new TileBasic(resourcePillarHorizontalGrey);
        pillarVerticalGrey = new TileBasic(resourcePillarVerticalGrey);
        pillarHorizontalPurple = new TileBasic(resourcePillarHorizontalPurple);
        pillarVerticalPurple = new TileBasic(resourcePillarVerticalPurple);
        pillarHorizontalPurpleBlue = new TileBasic(resourcePillarHorizontalPurpleBlue);
        pillarVerticalPurpleBlue = new TileBasic(resourcePillarVerticalPurpleBlue);

        plain = new ColourableTile(resourcePlain);
        assimilatedTile = new AssimilatedTile(resourceAssimilatedTile);
        assimilator = new AssimilatorTile();

        brightLog = new BrightLogTile();
        brightLeaves = new BrightLeavesTile();

        itemConduit = new ItemConduitTile(resourceItemConduit);

        glass = new ColourableTile(resourceGlass);
        glass.obscuresBackground = false;

        sunBlue = new TileBasic(resourceSunBlue);
        sunGreen = new TileBasic(resourceSunGreen);
        sunOrange = new TileBasic(resourceSunOrange);
        sunPurple = new TileBasic(resourceSunPurple);
        sunRed = new TileBasic(resourceSunRed);
        sunWhiteBlue = new TileBasic(resourceSunWhiteBlue);
        sunYellowBlue = new TileBasic(resourceSunYellowBlue);
        bombCannon = new BombCannonTile(resourceBombCannon);
        noteTile = new NoteTile(resourceNoteTile);
        jukebox = new JukeboxTile(resourceJukebox);

        itemCannon.register();
        funnelTile.register();
        dryFarmland.register();
        grass.register();
        longGrass.register();
        blueBerryBush.register();
        redBerryBush.register();
        uglyPlant.register();
        largeUglyPlant.register();
        drillBasic.register();
        drillCopper.register();
        phantomTile.register();
        pearlOre.register();
        slime.register();

        bricksBlue.register();
        bricksGreen.register();
        bricksGrey.register();
        bricksOrangeBrown.register();
        bricksPurple.register();
        bricksRed.register();
        bricksYellow.register();
        bricksYellowBrown.register();
        colourisedWoodAqua.register();
        colourisedWoodGoldBrown.register();
        colourisedWoodGreen.register();
        colourisedWoodGreenBrown.register();
        colourisedWoodPinkPurple.register();
        crystalBlue.register();
        crystalGreen.register();
        crystalPurple.register();
        crystalRed.register();
        crystalYellow.register();
        smoothStoneBlue.register();
        smoothStoneGreen.register();
        smoothStoneGrey.register();
        smoothStoneOrange.register();
        smoothStonePurple.register();
        smoothStoneYellow.register();
        pillarHorizontalBeige.register();
        pillarVerticalBeige.register();
        pillarHorizontalBlue.register();
        pillarVerticalBlue.register();
        pillarHorizontalGreen.register();
        pillarVerticalGreen.register();
        pillarHorizontalGrey.register();
        pillarVerticalGrey.register();
        pillarHorizontalPurple.register();
        pillarVerticalPurple.register();
        pillarHorizontalPurpleBlue.register();
        pillarVerticalPurpleBlue.register();

        plain.register();
        assimilatedTile.register();
        assimilator.register();

        brightLog.register();
        brightLeaves.register();
        brightSapling = new BrightSapling(); // Has to be down here because the sapling requires the log and leaves to be registered
        brightSapling.register();

        itemConduit.register();

        glass.register();
        sunBlue.register();
        sunGreen.register();
        sunOrange.register();
        sunPurple.register();
        sunRed.register();
        sunWhiteBlue.register();
        sunYellowBlue.register();

        bombCannon.register();
        noteTile.register();
        /*
        boolean failed = false;
        if (spotify.getAccessToken() == null) {
            File file = Paths.get(".", "rockbottom", "aoa.dat").toFile();
            if (file.exists()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String refreshToken = reader.readLine();
                    spotify.setRefreshToken(refreshToken);
                    JukeboxRunner.refreshToken();
                } catch (IOException | SpotifyWebApiException e) {
                    e.printStackTrace();
                    failed = true;
                }
            }
        }
        if (!failed) {
            jukebox.register();
        }*/
    }
}
