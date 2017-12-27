package alexanders.mods.aoa.init;

import alexanders.mods.aoa.tile.*;

import static alexanders.mods.aoa.AllOfAlex.createRes;

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


    public static void init() {
        itemCannon = new ItemCannonTile(createRes("item_cannon"));
        funnelTile = new FunnelTile(createRes("funnel"));
        FoliageAssets.dry_farmland.tile = dryFarmland = new VariantTile(Resources.dryFarmland);
        FoliageAssets.grass.tile = grass = new VariantTile(Resources.grass);
        FoliageAssets.long_grass.tile = longGrass = new VariantTile(Resources.longGrass);
        FoliageAssets.blue_berry_bush.tile = blueBerryBush = new BerryBushTile(Resources.blueBerryBush);
        FoliageAssets.red_berry_bush.tile = redBerryBush = new BerryBushTile(Resources.redBerryBush);
        FoliageAssets.ugly_plant.tile = uglyPlant = new VariantTile(Resources.uglyPlant);
        FoliageAssets.large_ugly_plant.tile = largeUglyPlant = new VariantTile(Resources.largeUglyPlant);
        drillBasic = new TileDrill(Resources.drill_basic, 30, .25f, .5f, 10);
        drillCopper = new TileDrill(Resources.drill_copper, 42, .5f, .25f, 20);
        phantomTile = new PhantomTile();
        pearlOre = new PearlOreTile();
        slime = new SlimeTile();

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
    }
}
