package alexanders.mods.aoa.render;

import alexanders.mods.aoa.entity.EntityDrill;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class DrillRenderer implements IEntityRenderer<EntityDrill> {
    public final IResourceName texture;

    public DrillRenderer(IResourceName texture) {
        this.texture = texture;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, EntityDrill entity, float x, float y, int light) {
        manager.getTexture(texture).draw(x, y - 4, 36 / 12f, 48 / 12f, new int[]{light, light, light, light});
    }
}
