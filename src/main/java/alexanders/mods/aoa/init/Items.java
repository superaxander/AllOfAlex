package alexanders.mods.aoa.init;

import alexanders.mods.aoa.item.*;

public class Items {
    public static ShearsItem shears;
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

    public static void init() {
        shears = new ShearsItem(Resources.shears);
        berryBlue = new SpeedBoostItem(Resources.blueBerryBush.addSuffix(".berry"));
        berryRed = new JumpBoostItem(Resources.redBerryBush.addSuffix(".berry"));
        drillBitBasic = new DrillBitItem(Resources.drill_bit_basic);
        drillBitCopper = new DrillBitItem(Resources.drill_bit_copper);
        pearlItem = new PearlItem();
        bouncyPearlItem = new BouncyPearlItem();
        rideablePearlItem = new RideablePearlItem();
        miningPearlItem = new MiningPearlItem();
        spikyPearlItem = new SpikyPearlItem();
        spawnPearlItem = new SpawnPearlItem();
        waypointPearlItem = new WaypointPearlItem();
        bridgingPearlItem = new BridgingPearlItem();
        shears.register();
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
    }
}
