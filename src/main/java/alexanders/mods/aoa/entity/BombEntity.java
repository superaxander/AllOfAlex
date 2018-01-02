package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.render.BombRenderer;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.bombResource;

public class BombEntity extends Entity {
    private static final int FUSE_TIME = 250;
    private static final int RADIUS = 4;
    private static final BoundBox UNIT_BOUND_BOX = new BoundBox(-1, -1, 1, 1);
    protected BombRenderer renderer;

    public BombEntity(IWorld world) {
        super(world);
        this.renderer = new BombRenderer(bombResource);
    }

    @Override
    public BombRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void update(IGameInstance game) {
        if (ticksExisted >= FUSE_TIME) {
            int r = getOrCreateAdditionalData().getInt("radius");
            if (r % 32 == 0)
                firstRound(r / 32);
            else if (r % 32 == 16) {
                if (r > 16)
                    secondRound((r - 16) / 32);
                else
                    secondRound(0);
            }
            getAdditionalData().addInt("radius", r + 1);
            if (r >= RADIUS * 32) {
                kill();
                super.update(game);
            }
        } else {
            super.update(game);
        }
    }

    @Override
    public void applyMotion() {
        this.motionY -= 0.015;

        this.motionX *= this.onGround ? 0.8 : 0.98;
        this.motionY *= 0.98;
    }

    private void firstRound(int r) {
        int x2 = r - 1;
        int y2 = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - (r << 1);

        while (x2 >= y2) {
            damageAndDestroy(x + x2, y + y2);
            damageAndDestroy(x + y2, y + x2);
            damageAndDestroy(x - y2, y + x2);
            damageAndDestroy(x - x2, y + y2);
            if (err <= 0) {
                y2++;
                err += dy;
                dy += 2;
            }
            if (err > 0) {
                x2--;
                dx += 2;
                err += dx - (r << 1);
            }
        }
        if (r == 0)
            damageAndDestroy(x, y);
    }

    private void secondRound(int r) {
        int x2 = r - 1;
        int y2 = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - (r << 1);

        while (x2 >= y2) {
            damageAndDestroy(x - x2, y - y2);
            damageAndDestroy(x - y2, y - x2);
            damageAndDestroy(x + y2, y - x2);
            damageAndDestroy(x + x2, y - y2);
            if (err <= 0) {
                y2++;
                err += dy;
                dy += 2;
            }
            if (err > 0) {
                x2--;
                dx += 2;
                err += dx - (r << 1);
            }
        }
        if (r == 0)
            damageAndDestroy(x, y);
    }

    protected void damageAndDestroy(double x, double y) {
        RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, x, y, 0, 0, .5f);
        world.destroyTile(Util.floor(x), Util.floor(y), TileLayer.MAIN, null, false);
        world.destroyTile(Util.floor(x), Util.floor(y), TileLayer.BACKGROUND, null, false);
    }
}
