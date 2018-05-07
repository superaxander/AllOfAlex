package alexanders.mods.aoa.render;

import alexanders.mods.aoa.entity.BombEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class BombRenderer implements IEntityRenderer<BombEntity> {
    private final ResourceName name;

    public BombRenderer(ResourceName name) {
        this.name = name;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, BombEntity entity, float x, float y, int light) {
        manager.getTexture(name.addPrefix("items."))
                .draw(x - .5f * (entity.ticksExisted / 500f), y - .5f * (entity.ticksExisted / 500f), .5f + (entity.ticksExisted / 500f), .5f + (entity.ticksExisted / 500f),
                      light);
    }
}
