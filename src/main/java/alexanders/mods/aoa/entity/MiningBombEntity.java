package alexanders.mods.aoa.entity;

import alexanders.mods.aoa.render.BombRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static alexanders.mods.aoa.init.Resources.miningBombResource;

public class MiningBombEntity extends BombEntity {

    public MiningBombEntity(IWorld world) {
        super(world);
        this.renderer = new BombRenderer(miningBombResource);
    }

    @Override
    protected void damageAndDestroy(double x, double y) {
        RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, x, y, 0, 0, .5f);
        world.destroyTile(Util.floor(x), Util.floor(y), TileLayer.MAIN, null, true);
        world.destroyTile(Util.floor(x), Util.floor(y), TileLayer.BACKGROUND, null, true);
    }
}
