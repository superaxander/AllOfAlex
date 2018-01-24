package alexanders.mods.aoa.render;

import alexanders.mods.aoa.Colours;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.particle.Particle;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.world.IWorld;

import static alexanders.mods.aoa.init.Resources.resourcePaintBomb;

public class PaintBombParticle extends Particle {
    private final Colours colour;

    public PaintBombParticle(IWorld world, double x, double y, double motionX, double motionY, int maxLife, Colours colour) {
        super(world, x, y, motionX, motionY, maxLife);
        this.colour = colour;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, float x, float y, int filter) {
        super.render(game, manager, g, x, y, filter);
        manager.getTexture(resourcePaintBomb.addPrefix("particles.")).draw(x, y, .25f, .25f, Colors.multiply(colour.colour, filter));
    }
}
