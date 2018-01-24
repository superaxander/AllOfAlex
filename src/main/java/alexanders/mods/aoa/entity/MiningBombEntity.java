package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.render.BombRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.miningBombResource;

public class MiningBombEntity extends BombEntity {

    public MiningBombEntity(IWorld world) {
        super(world);
        this.renderer = new BombRenderer(miningBombResource);
    }

    @Override
    protected void damageAndDestroy(int x, int y) {
        RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, x, y, 0, 0, .5f);
        world.destroyTile(x, y, TileLayer.MAIN, null, true);
        world.destroyTile(x, y, TileLayer.BACKGROUND, null, true);
    }
}
