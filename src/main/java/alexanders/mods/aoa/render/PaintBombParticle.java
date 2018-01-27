package alexanders.mods.aoa.render;

import alexanders.mods.aoa.Colours;
import alexanders.mods.aoa.init.Assets;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.particle.Particle;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.world.IWorld;

public class PaintBombParticle extends Particle {
    private final Colours colour;

    public PaintBombParticle(IWorld world, double x, double y, double motionX, double motionY, int maxLife, Colours colour) {
        super(world, x, y, motionX, motionY, maxLife);
        this.colour = colour;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, float x, float y, int filter) {
        super.render(game, manager, g, x, y, filter);
        Assets.colours.get(colour).draw(x, y, 1, 1, Colors.multiply(colour.colour, filter));
    }
}
