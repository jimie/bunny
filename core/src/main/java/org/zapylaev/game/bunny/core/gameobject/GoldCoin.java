package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.zapylaev.game.bunny.core.Assets;

public class GoldCoin extends AGameObject {
    private TextureRegion regGoldCoin;
    public boolean collected;

    public GoldCoin () {
        init();
    }

    private void init () {
        mDimension.set(0.5f, 0.5f);
        regGoldCoin = Assets.getInstance().goldCoin.goldCoin;
        // Set bounding box for collision detection
        bounds.set(0, 0, mDimension.x, mDimension.y);
        collected = false;
    }

    public void render (SpriteBatch batch) {
        if (collected) return;
        TextureRegion reg;
        reg = regGoldCoin;
        batch.draw(reg.getTexture(),
                mPosition.x, mPosition.y,
                mOrigin.x, mOrigin.y,
                mDimension.x, mDimension.y,
                mScale.x, mScale.y,
                mRotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }

    public int getScore() {
        return 100;
    }
}
