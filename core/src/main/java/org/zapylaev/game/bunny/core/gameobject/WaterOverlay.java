package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.zapylaev.game.bunny.core.Assets;

public class WaterOverlay extends AGameObject {
    private TextureRegion mRegWaterOverlay;
    private float mLength;

    public WaterOverlay(float length) {
        this.mLength = length;
        init();
    }

    private void init() {
        mDimension.set(mLength * 10, 3);
        mRegWaterOverlay = Assets.getInstance().levelDecoration.waterOverlay;
        mOrigin.x = -mDimension.x / 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg;
        reg = mRegWaterOverlay;
        batch.draw(reg.getTexture(),
                mPosition.x + mOrigin.x, mPosition.y + mOrigin.y,
                mOrigin.x, mOrigin.y,
                mDimension.x, mDimension.y,
                mScale.x, mScale.y,
                mRotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }
}
