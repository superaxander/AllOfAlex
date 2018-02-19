package alexanders.mods.aoa;

import alexanders.mods.aoa.gen.BrightTreeGen;
import alexanders.mods.aoa.gen.PearlOreGen;
import alexanders.mods.aoa.gen.SlimePoolGen;
import alexanders.mods.aoa.gen.VariantGen;
import alexanders.mods.aoa.init.*;
import alexanders.mods.aoa.render.ConfigGui;
import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.logging.Logger;

import static de.ellpeck.rockbottom.api.RockBottomAPI.WORLD_GENERATORS;

public class AllOfAlex implements IMod {
    public static final int PROGRESS_COLOR = Colors.rgb(0.1f, 0.5f, 0.1f);
    public static final int FIRE_COLOR = Colors.rgb(0.5f, 0.1f, 0.1f);
    public static final int FIXED_FUEL_VALUE = 10; // TODO: Change when fuel is reimplemented
    public static AllOfAlex instance;
    public Logger logger = RockBottomAPI.createLogger("AllOfAlex");

    public AllOfAlex() {
        instance = this;
    }

    public static IResourceName createRes(String name) {
        return RockBottomAPI.createRes(instance, name);
    }

    @Override
    public String getDisplayName() {
        return "AllOfAlex";
    }

    @Override
    public String getId() {
        return "aoa";
    }

    @Override
    public String getVersion() {
        return "0.8";
    }

    @Override
    public String getResourceLocation() {
        return "/assets/" + getId();
    }

    @Override
    public String getDescription() {
        return "All of my mods combined!";
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"Alexander Stekelenburg"};
    }

    @Override
    public Class<? extends Gui> getModGuiClass() {
        return null;//ConfigGui.class;
    }

    @Override
    public void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        Resources.init();
    }

    @Override
    public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        new ConduitLayer().register();
        Items.init();
        Tiles.init();
        Packets.init();
        Keys.init();
        Entities.init();
        Events.init(game, eventHandler);
        Recipes.init();
        Commands.init();
        MenuThemes.init();
        WORLD_GENERATORS.register(createRes("variant_gen"), VariantGen.class);
        WORLD_GENERATORS.register(createRes("bright_tree_gen"), BrightTreeGen.class);
        PearlOreGen.Companion.register();
        SlimePoolGen.Companion.register();
    }

    @Override
    public void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler) {
        FoliageAssets.init(assetManager);
        Assets.init(assetManager);
    }
}
