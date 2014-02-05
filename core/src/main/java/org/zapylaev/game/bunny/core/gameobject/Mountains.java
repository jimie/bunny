package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import org.zapylaev.game.bunny.core.Assets;

public class Mountains extends AGameObject {
    private TextureRegion mRegMountainLeft;
    private TextureRegion mRegMountainRight;
    private int mLength;

    public Mountains (int length) {
        mLength = length;
        init();
    }

    private void init () {
        mDimension.set(10, 2);
        mRegMountainLeft = Assets.getInstance().levelDecoration.mountainLeft;
        mRegMountainRight = Assets.getInstance().levelDecoration.mountainRight;
        // shift mountain and extend mLength
        mOrigin.x = -mDimension.x * 2;
        mLength += mDimension.x * 2;
    }

    private void drawMountain(SpriteBatch batch, float offsetX, float offsetY, float tintColor) {
        TextureRegion reg;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = mDimension.x * offsetX;
        float yRel = mDimension.y * offsetY;
        // mountains span the whole level
        int mountainLength = 0;
        mountainLength += MathUtils.ceil(mLength / (2 * mDimension.x));
        mountainLength += MathUtils.ceil(0.5f + offsetX);
        for (int i = 0; i < mountainLength; i++) {
            // mountain left
            reg = mRegMountainLeft;
            batch.draw(reg.getTexture(),
                    mOrigin.x + xRel, mPosition.y + mOrigin.y + yRel,
                    mOrigin.x, mOrigin.y,
                    mDimension.x, mDimension.y,
                    mScale.x, mScale.y,
                    mRotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
            xRel += mDimension.x;
            // mountain right
            reg = mRegMountainRight;
            batch.draw(reg.getTexture(),
                    mOrigin.x + xRel, mPosition.y + mOrigin.y + yRel,
                    mOrigin.x, mOrigin.y,
                    mDimension.x, mDimension.y,
                    mScale.x, mScale.y,
                    mRotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
            xRel += mDimension.x;
        }
        // reset color to white
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        // distant mountains (dark gray)
        drawMountain(batch, 0.5f, 0.5f, 0.5f);
        // distant mountains (gray)
        drawMountain(batch, 0.25f, 0.25f, 0.7f);
        // distant mountains (light gray)
        drawMountain(batch, 0.0f, 0.0f, 0.9f);
    }
}
