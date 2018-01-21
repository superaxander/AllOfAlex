package alexanders.mods.aoa.render;

import alexanders.mods.aoa.tile.BrightLogTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class LogRenderer extends DefaultTileRenderer<BrightLogTile> {
    private final IResourceName[] textures;

    public LogRenderer(IResourceName name) {
        super(name);
        StaticTileProps.LogType[] types = StaticTileProps.LogType.values();
        this.textures = new IResourceName[types.length];

        for (int i = 0; i < this.textures.length; ++i) {
            switch (types[i]) {
                case PLACED:
                    this.textures[i] = this.texture;
                    break;
                case ROOT_LEFT:
                case ROOT_RIGHT:
                case BRANCH_LEFT:
                case BRANCH_RIGHT:
                    this.textures[i] = this.texture.addSuffix(".branch");
                    break;
                case TRUNK_TOP:
                case TRUNK_MIDDLE:
                case TRUNK_BOTTOM:
                    this.textures[i] = this.texture.addSuffix("." + types[i].name().toLowerCase());
                    break;
            }
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, BrightLogTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        StaticTileProps.LogType variant = state.get(StaticTileProps.LOG_VARIANT);
        switch (variant) {
            case BRANCH_RIGHT:
                manager.getTexture(this.textures[variant.ordinal()]).getPositionalVariation(x, y).draw(renderX, renderY, renderX + scale, renderY + scale, 12, 0, 0, 12, light);
                break;
            case ROOT_LEFT:
                manager.getTexture(this.textures[variant.ordinal()]).getPositionalVariation(x, y).draw(renderX, renderY, renderX + scale, renderY + scale, 0, 12, 12, 0, light);
                break;
            case ROOT_RIGHT:
                manager.getTexture(this.textures[variant.ordinal()]).getPositionalVariation(x, y).draw(renderX, renderY, renderX + scale, renderY + scale, 12, 12, 0, 0, light);
                break;
            default:
                manager.getTexture(this.textures[variant.ordinal()]).getPositionalVariation(x, y).draw(renderX, renderY, renderX + scale, renderY + scale, 0, 0, 12, 12, light);
                break;
        }
    }
}
