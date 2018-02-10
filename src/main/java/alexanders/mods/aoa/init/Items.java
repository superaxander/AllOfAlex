package alexanders.mods.aoa.init;

import alexanders.mods.aoa.item.*;

import static alexanders.mods.aoa.init.Resources.*;

public class Items {
    public static ShearsItem shearsItem;
    public static SpeedBoostItem berryBlue;
    public static JumpBoostItem berryRed;
    public static DrillBitItem drillBitBasic;
    public static DrillBitItem drillBitCopper;
    public static PearlItem pearlItem;
    public static BouncyPearlItem bouncyPearlItem;
    public static RideablePearlItem rideablePearlItem;
    public static MiningPearlItem miningPearlItem;
    public static SpikyPearlItem spikyPearlItem;
    public static SpawnPearlItem spawnPearlItem;
    public static WaypointPearlItem waypointPearlItem;
    public static BridgingPearlItem bridgingPearlItem;
    public static BombItem bombItem;
    public static MiningBombItem miningBombItem;
    public static BombLauncherItem bombLauncherItem;
    public static PaintBombItem paintBombItem;
    public static MagnetItem magnetItem;
    public static FilterItem filter;

    public static void init() {
        shearsItem = new ShearsItem(shears);
        berryBlue = new SpeedBoostItem(resourceBlueBerryBush.addSuffix(".berry"));
        berryRed = new JumpBoostItem(resourceRedBerryBush.addSuffix(".berry"));
        drillBitBasic = new DrillBitItem(drill_bit_basic);
        drillBitCopper = new DrillBitItem(drill_bit_copper);
        pearlItem = new PearlItem();
        bouncyPearlItem = new BouncyPearlItem();
        rideablePearlItem = new RideablePearlItem();
        miningPearlItem = new MiningPearlItem();
        spikyPearlItem = new SpikyPearlItem();
        spawnPearlItem = new SpawnPearlItem();
        waypointPearlItem = new WaypointPearlItem();
        bridgingPearlItem = new BridgingPearlItem();
        bombItem = new BombItem(bombResource);
        miningBombItem = new MiningBombItem(miningBombResource);
        bombLauncherItem = new BombLauncherItem(bombLauncherResource);
        paintBombItem = new PaintBombItem(resourcePaintBomb);
        magnetItem = new MagnetItem(resourceMagnet);
        filter = new FilterItem(resourceFilter);
        shearsItem.register();
        berryBlue.register();
        berryRed.register();
        drillBitBasic.register();
        drillBitCopper.register();
        pearlItem.register();
        bouncyPearlItem.register();
        rideablePearlItem.register();
        miningPearlItem.register();
        spikyPearlItem.register();
        spawnPearlItem.register();
        waypointPearlItem.register();
        bridgingPearlItem.register();
        bombItem.register();
        miningBombItem.register();
        bombLauncherItem.register();
        paintBombItem.register();
        magnetItem.register();
        filter.register();
    }
}
