package alexanders.mods.aoa.render;

import alexanders.mods.aoa.entity.MagnetPulseEntity;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;

import static alexanders.mods.aoa.init.Resources.resourceMagnetPulse;

public class MagnetPulseRenderer implements IEntityRenderer<MagnetPulseEntity> {
    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, MagnetPulseEntity entity, float x, float y, int light) {
        BoundBox box = entity.getBoundingBox();
        manager.getTexture(resourceMagnetPulse).draw(x - (float) box.getWidth() / 2f, y - (float) box.getHeight() / 2f, (float) box.getWidth(), (float) box.getHeight(), light);
    }
}
