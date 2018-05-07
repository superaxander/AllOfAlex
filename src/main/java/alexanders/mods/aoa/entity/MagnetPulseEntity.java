package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.render.MagnetPulseRenderer;
import alexanders.mods.aoa.render.TeleportationParticle;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;

public class MagnetPulseEntity extends Entity {
    private MagnetPulseRenderer renderer = new MagnetPulseRenderer();

    public MagnetPulseEntity(IWorld world) {
        super(world);
    }

    @Override
    public void applyMotion() {
        motionX = 0;
        motionY = 0;
    }

    @Override
    public int getRenderPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public MagnetPulseRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        world.getEntities(currentBounds.expand(.2).copy().add(x, y), AbstractEntityItem.class, AbstractEntityItem::canPickUp).forEach((e) -> {
            double x = this.x - e.x;
            double y = this.y - e.y;
            double length = Util.distance(0, 0, x, y);
            e.motionX = 0.3 * (x / length);
            e.motionY = 0.3 * (y / length);
            game.getParticleManager().addParticle(new TeleportationParticle(world, e.x, e.y, -e.motionX * Util.RANDOM.nextFloat(), -e.motionY * Util.RANDOM.nextFloat(), 60));
        });
        if (ticksExisted >= 50) this.kill();
    }
}
