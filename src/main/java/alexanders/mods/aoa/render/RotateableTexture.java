package alexanders.mods.aoa.render;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.render.engine.TextureBank;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RotateableTexture implements ITexture {
    private ITexture texture;
    private double angle;
    private double sinAngle;
    private double cosAngle;
    private float centerX;
    private float centerY;

    public RotateableTexture(ITexture texture) {
        this.texture = texture;
    }

    @Override
    public void bind(TextureBank bank, boolean revertAfterBind) {
        texture.bind(bank, revertAfterBind);
    }

    @Override
    public void bind() {
        texture.bind();
    }

    @Override
    public void param(int param, int value) {
        texture.param(param, value);
    }

    @Override
    public int getId() {
        return texture.getId();
    }

    @Override
    public int getTextureWidth() {
        return texture.getTextureWidth();
    }

    @Override
    public int getTextureHeight() {
        return texture.getTextureHeight();
    }

    @Override
    public ByteBuffer getPixelData() {
        return texture.getPixelData();
    }

    @Override
    public void unbind(TextureBank bank, boolean revertAfterUnbind) {
        texture.unbind(bank, revertAfterUnbind);
    }

    @Override
    public void unbind() {
        texture.unbind();
    }

    public final void draw(float x, float y) {
        this.draw(x, y, 1.0F);
    }

    public final void draw(float x, float y, float scale) {
        this.draw(x, y, (float) this.getRenderWidth(), (float) this.getRenderHeight());
    }

    public final void draw(float x, float y, float width, float height) {
        this.draw(x, y, width, height, -1);
    }

    public final void draw(float x, float y, float width, float height, int[] light) {
        this.draw(x, y, width, height, light, -1);
    }

    public void draw(float x, float y, float width, float height, int filter) {
        this.draw(x, y, x + width, y + height, 0.0F, 0.0F, (float) this.getRenderWidth(), (float) this.getRenderWidth(), null, filter);
    }

    public void draw(float x, float y, float width, float height, int[] light, int filter) {
        this.draw(x, y, x + width, y + height, 0.0F, 0.0F, (float) this.getRenderWidth(), (float) this.getRenderHeight(), light, filter);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2) {
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, -1);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light) {
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, light, -1);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int filter) {
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, null, filter);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter) {
        this.draw(x, y, x, y2, x2, y2, x2, y, srcX, srcY, srcX2, srcY2, light, filter);
    }

    public void draw(float x, float y, float x2, float y2, float x3, float y3, float x4, float y4, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter) {
        texture.draw(x + rotateX(centerX, centerY, 0, 0, sinAngle, cosAngle),
                y + rotateY(centerX, centerY, 0, 0, sinAngle, cosAngle),
                x + rotateX(centerX, centerY, 0, y3 - y, sinAngle, cosAngle),
                y + rotateY(centerX, centerY, 0, y3 - y, sinAngle, cosAngle),
                x + rotateX(centerX, centerY, x4 - x, y3 - y, sinAngle, cosAngle),
                y + rotateY(centerX, centerY, x4 - x, y3 - y, sinAngle, cosAngle),
                x + rotateX(centerX, centerY, x4 - x, 0, sinAngle, cosAngle),
                y + rotateY(centerX, centerY, x4 - x, 0, sinAngle, cosAngle), srcX, srcY, srcX2, srcY2, light, filter);
    }

    public void rotate(double angle) {
        setRotation(this.angle + angle);
    }

    public double getRotation() {
        return this.angle;
    }
    
    public void setRotation(double angle) {
        angle %= 360;
        this.angle = angle;
        double rad = Math.toRadians(angle);
        this.sinAngle = Math.sin(rad);
        this.cosAngle = Math.cos(rad);
    }
    
    public void setRotationCenter(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    private float rotateX(float centerX, float centerY, float x, float y, double s, double c) {
        x -= centerX;
        y -= centerY;
        return (float) (centerX + x * c - y * s);
    }

    private float rotateY(float centerX, float centerY, float x, float y, double s, double c) {
        x -= centerX;
        y -= centerY;
        return (float) (centerY + x * s + y * c);
    }

    @Override
    public void setAdditionalData(Map<String, JsonElement> data) {
        texture.setAdditionalData(data);
    }

    @Override
    public void setVariations(List<ITexture> variations) {
        texture.setVariations(variations);
    }

    @Override
    public JsonElement getAdditionalData(String name) {
        return texture.getAdditionalData(name);
    }

    @Override
    public RotateableTexture getVariation(Random random) {
        return new RotateableTexture(texture.getVariation(random));
    }

    @Override
    public RotateableTexture getPositionalVariation(int x, int y) {
        return new RotateableTexture(texture.getPositionalVariation(x, y));
    }

    @Override
    public RotateableTexture getSubTexture(int x, int y, int width, int height) {
        return new RotateableTexture(texture.getSubTexture(x, y, width, height));
    }

    @Override
    public ITexture getSubTexture(int x, int y, int width, int height, boolean inheritVariations, boolean inheritData) {
        return new RotateableTexture(texture.getSubTexture(x, y, width, height, inheritVariations, inheritData));
    }

    @Override
    public int getTextureColor(int x, int y) {
        return texture.getTextureColor(x, y);
    }

    @Override
    public int getRenderWidth() {
        return texture.getRenderWidth();
    }

    @Override
    public int getRenderHeight() {
        return texture.getRenderHeight();
    }

    @Override
    public int getRenderOffsetX() {
        return texture.getRenderOffsetX();
    }

    @Override
    public int getRenderOffsetY() {
        return texture.getRenderOffsetY();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
